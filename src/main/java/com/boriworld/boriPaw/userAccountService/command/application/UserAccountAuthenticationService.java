package com.boriworld.boriPaw.userAccountService.command.application;


import com.boriworld.boriPaw.userAccountService.command.application.dto.LoginProcess;

import com.boriworld.boriPaw.userAccountService.command.domain.event.UserAccountEventPublisher;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.AccessTokenCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationToken;
import com.boriworld.boriPaw.userAccountService.command.domain.model.*;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.RefreshTokenRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.*;

import com.boriworld.boriPaw.userAccountService.command.domain.useCase.AccessTokenReissue;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.RefreshTokenCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountAuthenticationService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountPasswordEncoder userAccountPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationTokenService authenticationTokenService;
    private final AuthenticationTokenPayloadEncoder authenticationTokenPayloadEncoder;
    private final SecurityContextManager securityContextManager;
    private final UserAccountEventPublisher eventPublisher;

    @Transactional
    public AuthenticationToken processLogin(LoginProcess loginProcess) {
        log.info("login process");
        UserAccount userAccount = userAccountRepository.findByEmail(loginProcess.email())
                .orElseThrow(() -> new LoginFailException("이메일을 확인을 해봐요"))
                .updateLastLogin(loginProcess.password(), userAccountPasswordEncoder);

        AccessTokenCreate accessTokenCreate = new AccessTokenCreate(userAccount.getUserAccountId(), userAccount.getAuthority());
        AccessToken accessToken = AccessToken.createFrom(accessTokenCreate, authenticationTokenService, authenticationTokenPayloadEncoder);

        RefreshToken refreshToken = RefreshToken.createFrom(
                new RefreshTokenCreate(userAccount.getUserAccountId(), userAccount.getAuthority()), authenticationTokenService,
                authenticationTokenPayloadEncoder);
        refreshTokenRepository.save(refreshToken);
        return new AuthenticationToken(accessToken, refreshToken);
    }

    @Transactional
    public AuthenticationToken processReissueToken(final String refreshToken) {
        RefreshToken refreshTokenFromString = RefreshToken.createFromTokenString(refreshToken, authenticationTokenService);
        RefreshToken reissuedRefreshToken = refreshTokenRepository.findRefreshTokenId(refreshTokenFromString.getRefreshTokenId())
                .orElseThrow()
                .reissue(authenticationTokenService, authenticationTokenPayloadEncoder);

        refreshTokenRepository.save(reissuedRefreshToken);
        AccessTokenReissue accessTokenReissue = new AccessTokenReissue();
        return new AuthenticationToken(AccessToken.reissueFromRefresh(accessTokenReissue), reissuedRefreshToken);
    }

    @Transactional
    public void processLogout(Long userAccountId) {

    }

    public void processAuthenticationByAccessToken(final String tokenString) {
        AccessToken.processingAuthenticationFromTokenString(tokenString, authenticationTokenService, authenticationTokenPayloadEncoder, securityContextManager);
    }
}
