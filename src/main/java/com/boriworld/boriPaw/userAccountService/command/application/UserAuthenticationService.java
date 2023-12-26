package com.boriworld.boriPaw.userAccountService.command.application;


import com.boriworld.boriPaw.userAccountService.command.application.dto.LoginProcess;

import com.boriworld.boriPaw.userAccountService.command.domain.event.UserAccountEventPublisher;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.AuthenticationTokenException;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.NotRegisteredEmailException;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.AccessTokenCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationToken;
import com.boriworld.boriPaw.userAccountService.command.domain.model.*;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.RefreshTokenRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.*;

import com.boriworld.boriPaw.userAccountService.command.domain.useCase.RefreshTokenCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RefreshTokenId;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountPasswordEncoder userAccountPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationTokenService authenticationTokenService;
    private final AuthenticationTokenPayloadEncoder authenticationTokenPayloadEncoder;
    private final UserAuthenticationContextManager userAuthenticationContextManager;
    private final UserAccountEventPublisher eventPublisher;

    @Transactional
    public AuthenticationToken processLogin(LoginProcess loginProcess) {
        log.info("login process");
        UserAccount userAccount = getUserAccountByEmail(loginProcess.email())
                .login(new UserAccountLogin(loginProcess.password()), userAccountPasswordEncoder);
        UserAccount LoggedInAccount = userAccountRepository.update(userAccount);
        return createAuthenticationToken(LoggedInAccount);
    }

    @Transactional
    public AuthenticationToken processReissueToken(final String refreshTokenString) {

        RefreshToken fromTokenString = RefreshToken.fromTokenString(refreshTokenString, authenticationTokenService, authenticationTokenPayloadEncoder);

        RefreshToken reissue = refreshTokenRepository.findRefreshTokenId(fromTokenString.getRefreshTokenId())
                .orElseThrow()
                .reissue(authenticationTokenService, authenticationTokenPayloadEncoder);

        refreshTokenRepository.save(reissue);

        AccessToken accessToken = AccessToken.reissue(reissue, authenticationTokenService, authenticationTokenPayloadEncoder);

        return new AuthenticationToken(accessToken, reissue);
    }

    @Transactional
    public void processLogout(Long userAccountId) {
        RefreshToken refreshToken = refreshTokenRepository.findRefreshTokenId(RefreshTokenId.from(UserAccountId.of(userAccountId)))
                .orElseThrow();
        refreshTokenRepository.delete(refreshToken);
    }

    public void processAuthenticationByAccessToken(final String tokenString) throws AuthenticationTokenException {
        AccessToken.processingAuthenticationFromTokenString(tokenString, authenticationTokenService, authenticationTokenPayloadEncoder, userAuthenticationContextManager);
    }

    private UserAccount getUserAccountByEmail(final String email) {
        return userAccountRepository.findByEmail(email)
                .orElseThrow(() -> NotRegisteredEmailException.forMessage("이메일로 계정을 확인할수 없습니다."));
    }

    private AuthenticationToken createAuthenticationToken(UserAccount userAccount) {
        return new AuthenticationToken(
                createAccessToken(userAccount.getUserAccountId(), userAccount.getAuthority()),
                createAndSaveRefreshToken(userAccount.getUserAccountId(), userAccount.getAuthority()));
    }

    private RefreshToken createAndSaveRefreshToken(UserAccountId userAccountId, Authority authority) {
        RefreshTokenCreate refreshTokenCreate = new RefreshTokenCreate(userAccountId, authority);
        RefreshToken refreshToken = RefreshToken.from(refreshTokenCreate, authenticationTokenService, authenticationTokenPayloadEncoder);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    private AccessToken createAccessToken(UserAccountId userAccountId, Authority authority) {
        return AccessToken.createFrom(
                new AccessTokenCreate(userAccountId, authority),
                authenticationTokenService,
                authenticationTokenPayloadEncoder);
    }
}
