package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.*;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.exception.AlreadyUsedAccountNameException;
import com.boriworld.boriPaw.accountService.command.domain.exception.AlreadyUsedEmailException;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AccountCreateServiceTest {

    private AccountCreateService accountCreateService;
    private AccountRepository accountRepository;
    private AccountPasswordEncoder accountPasswordEncoder;
    private AccountEventPublisher accountEventPublisher;
    private EmailAuthenticationCodeGenerator emailAuthenticationCodeGenerator;
    private EmailAuthenticationCodeRepository emailAuthenticationCodeRepository;

    @BeforeEach
    void beforeEach() {
        accountRepository = new FakeAccountRepository();
        accountPasswordEncoder = new FakeAccountPasswordEncoder();
        accountEventPublisher = new FakeAccountEventPublisher();
        emailAuthenticationCodeGenerator = new FakeEmailAuthenticationCodeGenerator();
        emailAuthenticationCodeRepository = new FakeEmailAuthenticationCodeRepository();

        accountCreateService = new AccountCreateService(
                accountRepository,
                accountPasswordEncoder,
                accountEventPublisher,
                emailAuthenticationCodeGenerator,
                emailAuthenticationCodeRepository
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
        assertThatThrownBy(() -> accountCreateService.createAccount(accountCreate))
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
        assertThatThrownBy(() -> accountCreateService.createAccount(accountCreate))
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
        AccountId account = accountCreateService.createAccount(accountCreate);
        //then
        assertThat(account.getId()).isEqualTo(2L);


    }

}