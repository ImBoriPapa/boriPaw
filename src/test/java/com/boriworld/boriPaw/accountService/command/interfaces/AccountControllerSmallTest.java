package com.boriworld.boriPaw.accountService.command.interfaces;

import com.boriworld.boriPaw.accountService.command.application.AccountCreateService;
import com.boriworld.boriPaw.accountService.command.domain.*;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.interfaces.dto.AccountCreateResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountControllerSmallTest {

    private AccountController accountController;
    private AccountCreateService accountCreateService;

    @BeforeEach
    void beforeEach() {

        accountCreateService = new AccountCreateService(
                new FakeAccountRepository(),
                new FakeAccountPasswordEncoder(),
                new FakeAccountEventPublisher(),
                new FakeEmailAuthenticationCodeGenerator(),
                new FakeEmailAuthenticationCodeRepository()
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
        AccountCreate accountCreate = new AccountCreate(email, accountName, password, nickname);

        //when
        ResponseEntity<AccountCreateResponse> response = accountController.createAccount(accountCreate);

        //then
        assertThat(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(201)));
        assertThat(response.getBody().id()).isEqualTo(1L);

    }

}