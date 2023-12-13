package com.boriworld.boriPaw.userAccountService.command.domain.model;

import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeAuthenticationTokenService;
import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.RefreshTokenCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

class RefreshTokenSmallTest {

    private AuthenticationTokenService tokenService;
    private AuthenticationTokenPayloadEncoder encoder;

    @Test
    void RefreshTokenCreate_객체가_null_이면_NullPointException() throws Exception {
        //given
        RefreshTokenCreate refreshTokenCreate = null;
        tokenService = null;
        encoder = null;
        //when
        assertThatThrownBy(() -> RefreshToken.createFrom(refreshTokenCreate, tokenService, encoder))
                .isInstanceOf(NullPointerException.class);
        //then

    }

    @Test
    void AuthenticationTokenService_객체가_null_이면_NullPointException() throws Exception {
        //given
        final UserAccountId userAccountId = UserAccountId.of(123L);
        final Authority authority = Authority.USER;
        RefreshTokenCreate refreshTokenCreate = new RefreshTokenCreate(userAccountId, authority);
        tokenService = null;
        encoder = null;
        //when
        assertThatThrownBy(() -> RefreshToken.createFrom(refreshTokenCreate, tokenService, encoder))
                .isInstanceOf(NullPointerException.class);
        //then

    }

    @Test
    void AuthenticationTokenPayloadEncoder_객체가_null_이면_NullPointException() throws Exception {
        //given
        final UserAccountId userAccountId = UserAccountId.of(123L);
        final Authority authority = Authority.USER;
        RefreshTokenCreate refreshTokenCreate = new RefreshTokenCreate(userAccountId, authority);
        tokenService = new FakeAuthenticationTokenService(1000L, 5000L);
        encoder = null;
        //when
        assertThatThrownBy(() -> RefreshToken.createFrom(refreshTokenCreate, tokenService, encoder))
                .isInstanceOf(NullPointerException.class);
        //then

    }

    @Test
    void RefreshTokenCreate_객체로_RefreshToken_생성() throws Exception {
        //given
        final UserAccountId userAccountId = UserAccountId.of(123L);
        final Authority authority = Authority.USER;
        RefreshTokenCreate refreshTokenCreate = new RefreshTokenCreate(userAccountId, authority);

        tokenService = new FakeAuthenticationTokenService(1000L, 5000L);
        encoder = new FakeTokenPayloadEncoder();
        //when
        RefreshToken refreshToken = RefreshToken.createFrom(refreshTokenCreate, tokenService, encoder);
        //then
        assertThat(refreshToken).isNotNull();
        assertThat(refreshToken.getRefreshTokenId().getUserAccountId()).isEqualTo(userAccountId);
        assertThat(refreshToken.getTokenString()).isNotNull();
        assertThat(tokenService.validateToken(refreshToken.getTokenString())).isEqualTo(AuthenticationTokenStatus.ACCESS);
    }

}