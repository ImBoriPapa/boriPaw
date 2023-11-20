package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.model.Account;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountEventPublisher;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountPasswordEncoder;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountValidator;
import com.boriworld.boriPaw.accountService.command.exception.AlreadyUsedAccountNameException;
import com.boriworld.boriPaw.accountService.command.exception.AlreadyUsedEmailException;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import com.boriworld.boriPaw.common.validator.RequestObjectValidator;
import com.boriworld.boriPaw.testContanier.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AccountCreateServiceSmallTest {

    private AccountCreateService accountCreateService;
    private AccountRepository accountRepository;
    private AccountPasswordEncoder accountPasswordEncoder;
    private AccountEventPublisher accountEventPublisher;
    private RequestObjectValidator requestObjectValidator;
    private AccountValidator accountValidator;

    @BeforeEach
    void beforeEach() {
        accountRepository = new FakeAccountRepository();
        accountPasswordEncoder = new FakeAccountPasswordEncoder();
        accountEventPublisher = new FakeAccountEventPublisher();
        requestObjectValidator = new FakeRequestObjectValidator();
        accountValidator = new FakeAccountValidator(accountRepository);

        accountCreateService = new AccountCreateServiceImpl(
                accountRepository,
                accountPasswordEncoder,
                accountEventPublisher,
                requestObjectValidator,
                accountValidator

        );

        final String email = "duplicate@email.com";
        final String accountName = "duplicateName";
        final String password = "password1234";
        final String nickname = "boriPapa";

        AccountCreate accountCreate = new AccountCreate(email, accountName, password, nickname);

        accountRepository.save(Account.from(accountCreate, accountPasswordEncoder));
    }

    @Test
    void 중복_이메일로_계정생성시_AlreadyUsedEmailException() throws Exception {
        //given
        final String duplicateEmail = "duplicate@email.com";
        final String accountName = "boriPapaDa";
        final String password = "password1234";
        final String nickname = "boriPapa";
        AccountCreate accountCreate = new AccountCreate(duplicateEmail, accountName, password, nickname);
        //when

        //then
        assertThatThrownBy(() -> accountCreateService.processAccountCreation(accountCreate))
                .isInstanceOf(AlreadyUsedEmailException.class);
    }

    @Test
    void 중복_계정이름으로_계정생성시_AlreadyUsedAccountNameException() throws Exception {
        //given
        final String email = "email@email.com";
        final String duplicateAccountName = "duplicateName";
        final String password = "password1234";
        final String nickname = "boriPapa";
        AccountCreate accountCreate = new AccountCreate(email, duplicateAccountName, password, nickname);
        //when

        //then
        assertThatThrownBy(() -> accountCreateService.processAccountCreation(accountCreate))
                .isInstanceOf(AlreadyUsedAccountNameException.class);
    }

    @Test
    void AccountCreate_로_계정생성이_가능하다() throws Exception{
        //given
        final String email = "email@email.com";
        final String accountName = "boriPapaDa";
        final String password = "password1234";
        final String nickname = "boriPapa";
        AccountCreate accountCreate = new AccountCreate(email, accountName, password, nickname);
        //when
        AccountId account = accountCreateService.processAccountCreation(accountCreate);
        //then
        assertThat(account.getId()).isEqualTo(2L);


    }

}