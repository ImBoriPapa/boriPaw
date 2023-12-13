package com.boriworld.boriPaw.userAccountService.command.interfaces;

import com.boriworld.boriPaw.userAccountService.command.interfaces.request.UserAccountCreateRequest;
import com.boriworld.boriPaw.userAccountService.command.interfaces.response.UserAccountCreateResponse;
import com.boriworld.boriPaw.fakeTestComponent.TestComponentContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

class UserAccountManagementControllerSmallTest {
    private AccountManagementController accountManagementController;
    @BeforeEach
    void beforeEach() {
        accountManagementController = new TestComponentContainer().accountManagementController;
    }

    @Test
    void test() throws Exception {
        //given
        final String email = "duplicate@email.com";
        final String accountName = "duplicateName";
        final String password = "password1234";
        final String nickname = "boriPapa";

        UserAccountCreateRequest userAccountCreateRequest = new UserAccountCreateRequest(email, accountName, password, nickname);

        //when
        ResponseEntity<UserAccountCreateResponse> response = accountManagementController.createAccount(userAccountCreateRequest);
        //then
        assertThat(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(201)));
        assertThat(response.getBody().id()).isEqualTo(1L);

    }

}