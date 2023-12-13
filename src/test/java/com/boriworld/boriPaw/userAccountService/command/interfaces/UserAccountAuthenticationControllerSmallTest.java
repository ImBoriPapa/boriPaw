package com.boriworld.boriPaw.userAccountService.command.interfaces;

import com.boriworld.boriPaw.fakeTestComponent.TestComponentContainer;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.LoginRequest;
import com.boriworld.boriPaw.userAccountService.command.interfaces.response.LoginResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserAccountAuthenticationControllerSmallTest {

    private UserAccountAuthenticationController controller;

    @BeforeEach
    void beforeEach() {
        TestComponentContainer container = new TestComponentContainer();
        controller = container.userAccountAuthenticationController;
    }
    @Test
    void test1() throws Exception {
        //given
        final String email = "";
        final String password = "";
        LoginRequest loginRequest = new LoginRequest(email,password);
        //when
        ResponseEntity<LoginResponse> response = controller.login(loginRequest);
        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}