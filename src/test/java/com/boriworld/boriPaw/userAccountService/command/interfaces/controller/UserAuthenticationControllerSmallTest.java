package com.boriworld.boriPaw.userAccountService.command.interfaces.controller;

import com.boriworld.boriPaw.common.constant.AuthenticationTokenHeaderNames;
import com.boriworld.boriPaw.common.validator.ConstraintValidationFailureException;
import com.boriworld.boriPaw.fakeTestComponent.FakeComponentContainer;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.LoginRequest;
import com.boriworld.boriPaw.userAccountService.command.interfaces.response.LoginResponse;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



import java.net.HttpCookie;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserAuthenticationControllerSmallTest {

    private UserAuthenticationController controller;
    private FakeComponentContainer container;

    @BeforeEach
    void beforeEach() {
        container = new FakeComponentContainer();
        controller = container.userAuthenticationController;
    }

    @Test
    void givenNullLoginRequest_thenReturnCustomValidationFailException() throws Exception {
        //given
        LoginRequest loginRequest = null;
        //when
        AbstractThrowableAssert<?, ? extends Throwable> thrown = assertThatThrownBy(() -> controller.login(loginRequest));
        //then
        thrown.isInstanceOf(ConstraintValidationFailureException.class);
    }

    @Test
    void givenNullEmail_thenReturnCustomValidationFailException() throws Exception {
        //given
        final String email = null;
        final String password = "";
        LoginRequest request = new LoginRequest(email, password);
        //when
        AbstractThrowableAssert<?, ? extends Throwable> thrown = assertThatThrownBy(() -> controller.login(request));
        //then
        thrown.isInstanceOf(ConstraintValidationFailureException.class);
    }

    @Test
    void givenNullPassword_thenReturnCustomValidationFailException() throws Exception {
        //given
        final String email = "email@email.com";
        final String password = null;
        LoginRequest request = new LoginRequest(email, password);
        //when
        AbstractThrowableAssert<?, ? extends Throwable> thrown = assertThatThrownBy(() -> controller.login(request));
        //then
        thrown.isInstanceOf(ConstraintValidationFailureException.class);
    }

    @Test
    void givenLoginRequest_thenReturnAccessTokenInAuthorizationHeaderAndRefreshTokenInCookie() throws Exception {
        //given
        final String email = "email@email.com";
        final String username = "username";
        final String password = "email@email.com";
        final String nickname = "nickname";
        UserAccountCreate userAccountCreate = new UserAccountCreate(email, username, password, nickname);
        container.userAccountRepository.save(UserAccount.from(userAccountCreate, container.userAccountPasswordEncoder));
        LoginRequest request = new LoginRequest(email, password);
        //when
        ResponseEntity<LoginResponse> response = controller.login(request);
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        HttpCookie refreshTokenCookie = HttpCookie.parse(cookies.get(0)).get(0);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getFirst(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER)).isNotNull();
        assertThat(cookies).isNotEmpty().hasSize(1);
        assertThat(refreshTokenCookie.getName()).isEqualTo(AuthenticationTokenHeaderNames.REFRESH_TOKEN_COOKIE_NAME);

    }
}