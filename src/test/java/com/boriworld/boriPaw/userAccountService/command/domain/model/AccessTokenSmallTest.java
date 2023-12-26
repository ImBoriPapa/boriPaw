package com.boriworld.boriPaw.userAccountService.command.domain.model;



import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeAuthenticationTokenService;
import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.AccessTokenCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAuthenticationContextManager;

import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AccessTokenSmallTest {
    private AuthenticationTokenService tokenService;
    private AuthenticationTokenPayloadEncoder payloadEncoder;
    private UserAuthenticationContextManager userAuthenticationContextManager;

    @Test
    @DisplayName("when create by createFrom() if AccessTokenCreate is null: NullPointerException")
    void test1() throws Exception {
        //given
        UserAccountId userAccountId = UserAccountId.of(123L);
        Authority authority = Authority.USER;
        AccessTokenCreate create = null;
        //when
        //then
        assertThatThrownBy(() -> AccessToken.createFrom(create, tokenService, payloadEncoder))
                .isInstanceOf(NullPointerException.class);

    }

    @Test
    @DisplayName("when create by createFrom() if AuthenticationTokenService is null: NullPointerException")
    void test2() throws Exception {
        //given
        UserAccountId userAccountId = UserAccountId.of(123L);
        Authority authority = Authority.USER;
        AccessTokenCreate create = new AccessTokenCreate(userAccountId, authority);
        tokenService = null;
        //when
        //then
        assertThatThrownBy(() -> AccessToken.createFrom(create, tokenService, payloadEncoder))
                .isInstanceOf(NullPointerException.class);

    }

    @Test
    @DisplayName("when create by createFrom() if AuthenticationTokenPayloadEncoder is null: NullPointerException")
    void test3() throws Exception {
        //given
        UserAccountId userAccountId = UserAccountId.of(123L);
        Authority authority = Authority.USER;
        AccessTokenCreate create = new AccessTokenCreate(userAccountId, authority);
        tokenService = new FakeAuthenticationTokenService(1, 3);
        payloadEncoder = null;
        //when
        //then
        assertThatThrownBy(() -> AccessToken.createFrom(create, tokenService, payloadEncoder))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("when create by AccessToken.createFrom()")
    void test4() throws Exception {
        //given
        UserAccountId userAccountId = UserAccountId.of(123L);
        Authority authority = Authority.USER;
        AccessTokenCreate create = new AccessTokenCreate(userAccountId, authority);
        final long ACCESS_TOKEN_LIFE_SECOND = 1000 * 60 * 10;
        final long REFRESH_TOKEN_LIFE_SECOND = 1000 * 60 * 60 * 24 * 7;
        tokenService = new FakeAuthenticationTokenService(ACCESS_TOKEN_LIFE_SECOND, REFRESH_TOKEN_LIFE_SECOND);
        payloadEncoder = new FakeTokenPayloadEncoder();
        //when
        AccessToken accessToken = AccessToken.createFrom(create, tokenService, payloadEncoder);
        //then
        assertThat(accessToken).isNotNull();
        assertThat(accessToken.getTokenString()).isNotNull();
        assertThat(accessToken.getUserAccountId()).isEqualTo(userAccountId);
        assertThat(accessToken.getAuthority()).isEqualTo(authority);
        assertThat(tokenService.validateToken(accessToken.getTokenString())).isEqualTo(AuthenticationTokenStatus.ACCESS);
    }

}