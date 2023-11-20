package com.boriworld.boriPaw.accountService.command.interfaces;

import com.boriworld.boriPaw.accountService.command.application.AccountCreateService;
import com.boriworld.boriPaw.accountService.command.application.AccountCreateServiceImpl;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.interfaces.request.AccountCreateRequest;
import com.boriworld.boriPaw.accountService.command.interfaces.response.AccountCreateResponse;
import com.boriworld.boriPaw.testContanier.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

class AccountControllerSmallTest {

    private AccountController accountController;
    private AccountCreateService accountCreateService;

    @BeforeEach
    void beforeEach() {

        accountCreateService = new AccountCreateServiceImpl(
                new FakeAccountRepository(),
                new FakeAccountPasswordEncoder(),
                new FakeAccountEventPublisher(),
                new FakeRequestObjectValidator(),
                new FakeAccountValidator(new FakeAccountRepository())
        );

        accountController = new AccountController(accountCreateService);
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
        ResponseEntity<AccountCreateResponse> response = accountController.createAccount(accountCreateRequest);
        //then
        assertThat(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(201)));
        assertThat(response.getBody().id()).isEqualTo(1L);

    }

}