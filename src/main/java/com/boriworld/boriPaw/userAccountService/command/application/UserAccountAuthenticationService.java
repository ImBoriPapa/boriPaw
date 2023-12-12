package com.boriworld.boriPaw.userAccountService.command.application;


import com.boriworld.boriPaw.userAccountService.command.application.dto.LoginProcessRequest;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AccessTokenCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.model.AuthenticationToken;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.RefreshTokenRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.*;

import com.boriworld.boriPaw.userAccountService.command.domain.value.AccessToken;
import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
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

    @Transactional
    public AuthenticationToken processLogin(LoginProcessRequest loginProcessRequest) {
        log.info("login process");
        UserAccount userAccount = userAccountRepository.findByEmail(loginProcessRequest.email())
                .orElseThrow(() -> new LoginFailException("이메일을 확인을 해봐요"))
                .updateLastLogin(loginProcessRequest.password(), userAccountPasswordEncoder);

        AccessTokenCreate accessTokenCreate = new AccessTokenCreate(userAccount.getUserAccountId(), userAccount.getAuthority());

        AccessToken accessToken = AccessToken.from(accessTokenCreate, authenticationTokenService, authenticationTokenPayloadEncoder);

        RefreshToken refreshToken = RefreshToken.create(userAccount, authenticationTokenService, authenticationTokenPayloadEncoder);
        refreshTokenRepository.save(refreshToken);

        return new AuthenticationToken(accessToken, refreshToken);
    }

    @Transactional
    public AuthenticationToken processReissueToken(final String refreshToken) {
        RefreshToken refreshTokenFromString = RefreshToken.fromTokenString(refreshToken, authenticationTokenService);
        RefreshToken reissuedRefreshToken = refreshTokenRepository.findRefreshTokenId(refreshTokenFromString.getRefreshTokenId())
                .orElseThrow()
                .reissue(authenticationTokenService, authenticationTokenPayloadEncoder);

        refreshTokenRepository.save(reissuedRefreshToken);

        return new AuthenticationToken(AccessToken.reissueFromRefresh(reissuedRefreshToken), reissuedRefreshToken);
    }

    public void processAuthenticationByAccessToken(String token) {
        AccessToken accessToken = AccessToken.createFromTokenString(token);
        accessToken.processingAuthentication(authenticationTokenService, authenticationTokenPayloadEncoder, securityContextManager);
    }


}
