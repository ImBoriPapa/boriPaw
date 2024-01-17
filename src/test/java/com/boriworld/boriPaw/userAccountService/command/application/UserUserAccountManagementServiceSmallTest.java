package com.boriworld.boriPaw.userAccountService.command.application;

import com.boriworld.boriPaw.userAccountService.command.domain.event.UserAccountEventPublisher;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserProfileRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountCreateValidator;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;

import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

import com.boriworld.boriPaw.userAccountService.command.domain.value.AccountStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.DuplicateUsernameException;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.DuplicateEmailException;
import com.boriworld.boriPaw.fakeTestComponent.FakeComponentContainer;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class UserUserAccountManagementServiceSmallTest {
    private FakeComponentContainer componentContainer;
    private UserAccountManagementService userAccountManagementService;
    private UserAccountRepository userAccountRepository;
    private UserProfileRepository userProfileRepository;
    private UserAccountPasswordEncoder passwordEncoder;
    private UserAccountEventPublisher publisher;

    @BeforeEach
    void beforeEach() {
        componentContainer = new FakeComponentContainer();
        userAccountRepository = componentContainer.userAccountRepository;
        passwordEncoder = componentContainer.userAccountPasswordEncoder;
        publisher = componentContainer.userAccountEventPublisher;
        userProfileRepository = new FakeUserProfileRepository();
        userAccountManagementService = new UserAccountManagementService(
                userAccountRepository, userProfileRepository,
                passwordEncoder, publisher, Set.of(
                new UserAccountCreateValidator(userAccountRepository)
        ));
    }

    UserAccount sampleData() {
        final String email = "tester@email.com";
        final String password = "password1234";
        final String nickname = "testerName";

        UserAccountCreate userAccountCreate = new UserAccountCreate(email, password, nickname);
        return userAccountRepository.save(UserAccount.from(userAccountCreate, passwordEncoder));
    }

    @Test
    void givenDuplicateEmail_thenThrowDuplicateEmailException() throws Exception {
        //given
        UserAccount sampleUser = sampleData();
        final String duplicateEmail = sampleUser.getEmail();
        final String password = "password1234";
        final String nickname = "boriPapa";
        UserAccountCreate userAccountCreate = new UserAccountCreate(duplicateEmail, password, nickname);
        //when

        //then
        assertThatThrownBy(() -> userAccountManagementService.processUserAccountCreation(userAccountCreate))
                .isInstanceOf(DuplicateEmailException.class);
    }


    @Test
    void givenUserAccountObjectAndUserPasswordEncoder_thenCreate_UserAccountAndReturnAccountId() throws Exception {
        //given
        final String email = "email@email.com";
        final String password = "password1234";
        final String nickname = "boriPapa";
        UserAccountCreate userAccountCreate = new UserAccountCreate(email, password, nickname);
        //when
        UserAccountId userAccountId = userAccountManagementService.processUserAccountCreation(userAccountCreate);
        UserAccount userAccount = userAccountRepository.findById(userAccountId).orElseThrow();
        //then
        assertThat(userAccountId).isEqualTo(userAccount.getUserAccountId());
        assertThat(userAccount.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE);
    }

}