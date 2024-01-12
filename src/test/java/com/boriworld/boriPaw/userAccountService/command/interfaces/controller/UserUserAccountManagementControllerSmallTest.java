package com.boriworld.boriPaw.userAccountService.command.interfaces.controller;


import com.boriworld.boriPaw.userAccountService.command.interfaces.request.UserAccountCreateRequest;
import com.boriworld.boriPaw.userAccountService.command.interfaces.response.UserAccountCreateResponse;
import com.boriworld.boriPaw.fakeTestComponent.FakeComponentContainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

class UserUserAccountManagementControllerSmallTest {
    private UserAccountManagementController userAccountManagementController;

    @BeforeEach
    void beforeEach() {
        userAccountManagementController = new FakeComponentContainer().userAccountManagementController;
    }

    @Test
    void givenUserAccountCreateRequest_thenCreateUserAccountAndReturnUserAccountId() throws Exception {
        //given
        final String email = "duplicate@email.com";
        final String password = "password1234";
        final String nickname = "boriPapa";

        UserAccountCreateRequest userAccountCreateRequest = new UserAccountCreateRequest(email, password, nickname);

        //when
        ResponseEntity<UserAccountCreateResponse> response = userAccountManagementController.createUserAccount(userAccountCreateRequest);
        //then
        assertThat(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(201)));
        assertThat(response.getBody().userAccountId()).isEqualTo(1L);

    }

}