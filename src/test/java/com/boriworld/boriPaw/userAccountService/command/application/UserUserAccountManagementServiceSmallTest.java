package com.boriworld.boriPaw.userAccountService.command.application;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;

import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

import com.boriworld.boriPaw.userAccountService.command.domain.value.AccountStatus;
import com.boriworld.boriPaw.userAccountService.command.exception.AlreadyUsedAccountNameException;
import com.boriworld.boriPaw.userAccountService.command.exception.AlreadyUsedEmailException;
import com.boriworld.boriPaw.common.validator.CustomValidationFailException;
import com.boriworld.boriPaw.fakeTestComponent.TestComponentContainer;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserUserAccountManagementServiceSmallTest {

    private UserAccountManagementService userAccountManagementService;
    private UserAccountRepository userAccountRepository;

    @BeforeEach
    void beforeEach() {
        TestComponentContainer componentContainer = new TestComponentContainer();
        userAccountRepository = componentContainer.userAccountRepository;
        userAccountManagementService = componentContainer.userAccountManagementService;
        final String email = "duplicate@email.com";
        final String accountName = "duplicateName";
        final String password = "password1234";
        final String nickname = "boriPapa";

        UserAccountCreate userAccountCreate = new UserAccountCreate(email, accountName, password, nickname);

        userAccountRepository.save(UserAccount.from(userAccountCreate, componentContainer.userAccountPasswordEncoder));
    }

    @Test
    void AccountCreate_객체가_null_이면_CustomValidationFailException_예외가_발생한다() throws Exception {
        //given

        //when

        //then
        assertThatThrownBy(() -> userAccountManagementService.processUserAccountCreation(null))
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
        UserAccountCreate userAccountCreate = new UserAccountCreate(email, accountName, password, nickname);

        //then
        assertThatThrownBy(() -> userAccountManagementService.processUserAccountCreation(userAccountCreate))
                .isInstanceOf(CustomValidationFailException.class);
    }

    @Test
    void 중복_이메일로_계정생성시_AlreadyUsedEmailException() throws Exception {
        //given
        final String duplicateEmail = "duplicate@email.com";
        final String accountName = "boriPapaDa";
        final String password = "password1234";
        final String nickname = "boriPapa";
        UserAccountCreate userAccountCreate = new UserAccountCreate(duplicateEmail, accountName, password, nickname);
        //when

        //then
        assertThatThrownBy(() -> userAccountManagementService.processUserAccountCreation(userAccountCreate))
                .isInstanceOf(AlreadyUsedEmailException.class);
    }

    @Test
    void 중복_계정이름으로_계정생성시_AlreadyUsedAccountNameException() throws Exception {
        //given
        final String email = "email@email.com";
        final String duplicateAccountName = "duplicateName";
        final String password = "password1234";
        final String nickname = "boriPapa";
        UserAccountCreate userAccountCreate = new UserAccountCreate(email, duplicateAccountName, password, nickname);
        //when

        //then
        assertThatThrownBy(() -> userAccountManagementService.processUserAccountCreation(userAccountCreate))
                .isInstanceOf(AlreadyUsedAccountNameException.class);
    }


    @Test
    void AccountCreate_로_계정생성이_가능하다() throws Exception {
        //given
        final String email = "email@email.com";
        final String accountName = "boriPapaDa";
        final String password = "password1234";
        final String nickname = "boriPapa";
        UserAccountCreate userAccountCreate = new UserAccountCreate(email, accountName, password, nickname);
        //when
        UserAccountId userAccountId = userAccountManagementService.processUserAccountCreation(userAccountCreate);
        UserAccount userAccount = userAccountRepository.findById(userAccountId).orElseThrow();
        //then
        assertThat(userAccountId.getId()).isEqualTo(2L);
        assertThat(userAccount.getAccountStatus()).isEqualTo(AccountStatus.PENDING);
    }

}