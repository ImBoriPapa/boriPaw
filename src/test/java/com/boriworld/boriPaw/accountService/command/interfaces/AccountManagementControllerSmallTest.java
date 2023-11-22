package com.boriworld.boriPaw.accountService.command.interfaces;

import com.boriworld.boriPaw.accountService.command.application.AccountManagementService;
import com.boriworld.boriPaw.accountService.command.application.AccountManagementServiceImpl;
import com.boriworld.boriPaw.accountService.command.interfaces.request.AccountCreateRequest;
import com.boriworld.boriPaw.accountService.command.interfaces.response.AccountCreateResponse;
import com.boriworld.boriPaw.testContanier.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

class AccountManagementControllerSmallTest {

    private AccountManagementController accountManagementController;
    private AccountManagementService accountManagementService;

    @BeforeEach
    void beforeEach() {

        accountManagementService = new AccountManagementServiceImpl(
                new FakeAccountRepository(),
                new FakeAccountPasswordEncoder(),
                new FakeAccountEventPublisher(),
                new FakeRequestConstraintValidator(),
                new FakeAccountValidator(new FakeAccountRepository())
        );

        accountManagementController = new AccountManagementController(accountManagementService);
    }

    @Test
    void test() throws Exception {
        //given
        final String email = "duplicate@email.com";
        final String accountName = "duplicateName";
        final String password = "password1234";
        final String nickname = "boriPapa";

        AccountCreateRequest accountCreateRequest = new AccountCreateRequest(email, accountName, password, nickname);

        //when
        ResponseEntity<AccountCreateResponse> response = accountManagementController.createAccount(accountCreateRequest);
        //then
        assertThat(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(201)));
        assertThat(response.getBody().id()).isEqualTo(1L);

    }

}