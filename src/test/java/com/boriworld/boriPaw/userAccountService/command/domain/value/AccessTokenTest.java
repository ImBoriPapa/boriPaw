package com.boriworld.boriPaw.userAccountService.command.domain.value;

import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeSecurityContextManger;
import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeTokenPayloadEncoder;
import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeAuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AccessTokenCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.service.SecurityContextManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class AccessTokenTest {
    private AuthenticationTokenService tokenService;
    private AuthenticationTokenPayloadEncoder payloadEncoder;
    private SecurityContextManager securityContextManager;

    @Test
    @DisplayName("AccessTokenCreate 과 AuthenticationTokenService, AuthenticationTokenPayloadEncoder 로 AccessToken 생성할 수 있다.")
    void test1() throws Exception {
        //given
        UserAccountId userAccountId = UserAccountId.of(1L);
        Authority authority = Authority.USER;
        AccessTokenCreate accessTokenCreate = new AccessTokenCreate(userAccountId, authority);
        tokenService = new FakeAuthenticationTokenService(3, 3);
        payloadEncoder = new FakeTokenPayloadEncoder();
        //when
        AccessToken accessToken = AccessToken.from(accessTokenCreate, tokenService, payloadEncoder);
        //then
        assertThat(accessToken).isNotNull();
        assertThat(accessToken.getTokenString()).isNotNull();
        assertThat(accessToken.getUserAccountId(tokenService)).isEqualTo(userAccountId);
    }

    @Test
    @DisplayName("dsds")
    void test2() throws Exception {
        //given
        tokenService = new FakeAuthenticationTokenService(3, 3);
        payloadEncoder = new FakeTokenPayloadEncoder();
        securityContextManager = new FakeSecurityContextManger();
        String subject = String.valueOf(12131L);
        Authority authority = Authority.USER;
        AuthenticationTokenCredentials credentials = new AuthenticationTokenCredentials(subject, Map.of("authority", authority.name()));
        String tokenString = tokenService.generateTokenString(credentials, AuthenticationTokenType.ACCESS_TOKEN);
        //when
        AccessToken.createFromTokenString(tokenString).processingAuthentication(tokenService, payloadEncoder, securityContextManager);
        //then
        assertThat(securityContextManager.getAuthentication()).isNotNull();
        assertThat((UserAccountId)securityContextManager.getAuthentication().getPrincipal()).isEqualTo(UserAccountId.of(Long.parseLong(subject)));

    }

}