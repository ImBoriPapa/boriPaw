package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.model.UserAccount;

import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;

import com.boriworld.boriPaw.accountService.command.domain.value.AccountStatus;
import com.boriworld.boriPaw.accountService.command.exception.AlreadyUsedAccountNameException;
import com.boriworld.boriPaw.accountService.command.exception.AlreadyUsedEmailException;
import com.boriworld.boriPaw.common.validator.CustomValidationFailException;
import com.boriworld.boriPaw.testComponent.TestComponentContainer;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserAccountManagementServiceSmallTest {

    private AccountManagementService accountManagementService;
    private AccountRepository accountRepository;

    @BeforeEach
    void beforeEach() {
        TestComponentContainer componentContainer = new TestComponentContainer();
        accountRepository = componentContainer.accountRepository;
        accountManagementService = componentContainer.accountManagementService;
        final String email = "duplicate@email.com";
        final String accountName = "duplicateName";
        final String password = "password1234";
        final String nickname = "boriPapa";

        AccountCreate accountCreate = new AccountCreate(email, accountName, password, nickname);

        accountRepository.save(UserAccount.from(accountCreate, componentContainer.accountPasswordEncoder));
    }

    @Test
    void AccountCreate_객체가_null_이면_CustomValidationFailException_예외가_발생한다() throws Exception {
        //given

        //when

        //then
        assertThatThrownBy(() -> accountManagementService.processAccountCreation(null))
                .isInstanceOf(CustomValidationFailException.class);

    }

    @Test
    void 이메일형식이_아닐_경우() throws Exception {
        //given
        final String email = "emailEmail.com";
        final String accountName = "accountName";
        final String password = "password1234";
        final String nickname = "nickname";
        //when
        AccountCreate accountCreate = new AccountCreate(email, accountName, password, nickname);

        //then
        assertThatThrownBy(() -> accountManagementService.processAccountCreation(accountCreate))
                .isInstanceOf(CustomValidationFailException.class);
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
        assertThatThrownBy(() -> accountManagementService.processAccountCreation(accountCreate))
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
        assertThatThrownBy(() -> accountManagementService.processAccountCreation(accountCreate))
                .isInstanceOf(AlreadyUsedAccountNameException.class);
    }


    @Test
    void AccountCreate_로_계정생성이_가능하다() throws Exception {
        //given
        final String email = "email@email.com";
        final String accountName = "boriPapaDa";
        final String password = "password1234";
        final String nickname = "boriPapa";
        AccountCreate accountCreate = new AccountCreate(email, accountName, password, nickname);
        //when
        AccountId accountId = accountManagementService.processAccountCreation(accountCreate);
        UserAccount userAccount = accountRepository.findById(accountId).orElseThrow();
        //then
        assertThat(accountId.getId()).isEqualTo(2L);
        assertThat(userAccount.getAccountStatus()).isEqualTo(AccountStatus.PENDING);
    }

}