package com.boriworld.boriPaw.userAccountService.command.domain;

import com.boriworld.boriPaw.userAccountService.command.domain.exception.LoginFailException;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccountLogin;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.value.*;
import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeUserAccountPasswordEncoder;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserAccountSmallTest {
    UserAccountPasswordEncoder userAccountPasswordEncoder;

    @Test
    void givenNullAccountCreate_thenThrowNullPointException() throws Exception {
        //given
        UserAccountCreate userAccountCreate = null;
        UserAccountPasswordEncoder userAccountPasswordEncoder = null;
        //when

        //then
        assertThatThrownBy(() -> UserAccount.from(userAccountCreate, userAccountPasswordEncoder))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void givenNullAccountPasswordEncoder_thenThrowNullPointException() throws Exception {
        //given
        final String email = "";
        final String username = "";
        final String password = "";
        final String nickname = "";
        UserAccountCreate userAccountCreate = new UserAccountCreate(email, username, password, nickname);
        userAccountPasswordEncoder = null;
        //when

        //then
        assertThatThrownBy(() -> UserAccount.from(userAccountCreate, userAccountPasswordEncoder))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void givenUserAccountCreateAndUserAccountPasswordEncoder_thenCreateUserAccount() throws Exception {
        //given
        final String email = "boriPapa@google.com";
        final String accountName = "boriPapaDa";
        final String password = "password1234!@";
        final String nickname = "boriPapa";

        UserAccountCreate userAccountCreate = new UserAccountCreate(email, accountName, password, nickname);
        userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();
        //when
        UserAccount userAccount = UserAccount.from(userAccountCreate, userAccountPasswordEncoder);
        //then
        assertAll(
                () -> assertThat(userAccount.getUserAccountId()).isNull(),
                () -> assertThat(userAccount.getEmail()).isEqualTo(email),
                () -> assertThat(userAccount.getUsername()).isEqualTo(accountName),
                () -> assertThat(userAccount.getPassword()).isNotEqualTo(password),
                () -> assertThat(userAccount.getUserProfile().getNickname()).isEqualTo(nickname),
                () -> assertThat(userAccount.getUserProfile().getProfileImage()).isNull(),
                () -> assertThat(userAccount.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE),
                () -> assertThat(userAccount.getPasswordStatus()).isEqualTo(PasswordStatus.STEADY),
                () -> assertThat(userAccount.getAuthority()).isEqualTo(Authority.USER),
                () -> assertThat(userAccount.getCreatedAt()).isNotNull(),
                () -> assertThat(userAccount.getUpdatedAt()).isNull(),
                () -> assertThat(userAccount.getLastLoginAt()).isNull()
        );

    }

    @Test
    void givenUserAccountInitialize_thenInitializeUserAccount() throws Exception {
        //given
        final UserAccountId userAccountId = UserAccountId.of(123L);
        final String email = "boriPapa@gmail.com";
        final String accountName = "username";
        final String password = "password1234!@";
        final String nickname = "boriPapa";
        final String profileImage = "imageurl";
        final UserProfile userProfile = UserProfile.of(nickname, profileImage);
        final AccountStatus accountStatus = AccountStatus.ACTIVE;
        final PasswordStatus passwordStatus = PasswordStatus.STEADY;
        final Authority authority = Authority.USER;
        final LocalDateTime createdAt = LocalDateTime.now();
        final LocalDateTime updatedAt = LocalDateTime.now();
        final LocalDateTime lastLoginAt = LocalDateTime.now();

        UserAccountInitialize initialize = UserAccountInitialize.builder()
                .userAccountId(userAccountId)
                .email(email)
                .userName(accountName)
                .password(password)
                .userProfile(userProfile)
                .accountStatus(accountStatus)
                .passwordStatus(passwordStatus)
                .authority(authority)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .lastLoginAt(lastLoginAt)
                .build();
        //when
        UserAccount userAccount = UserAccount.initialize(initialize);
        //then
        assertAll(
                () -> assertThat(userAccount.getUserAccountId()).isEqualTo(userAccountId),
                () -> assertThat(userAccount.getEmail()).isEqualTo(email),
                () -> assertThat(userAccount.getUsername()).isEqualTo(accountName),
                () -> assertThat(userAccount.getPassword()).isEqualTo(password),
                () -> assertThat(userAccount.getUserProfile().getNickname()).isEqualTo(nickname),
                () -> assertThat(userAccount.getUserProfile().getProfileImage()).isEqualTo(profileImage),
                () -> assertThat(userAccount.getAccountStatus()).isEqualTo(accountStatus),
                () -> assertThat(userAccount.getPasswordStatus()).isEqualTo(passwordStatus),
                () -> assertThat(userAccount.getAuthority()).isEqualTo(authority),
                () -> assertThat(userAccount.getCreatedAt()).isEqualTo(createdAt),
                () -> assertThat(userAccount.getUpdatedAt()).isEqualTo(updatedAt),
                () -> assertThat(userAccount.getLastLoginAt()).isEqualTo(lastLoginAt)
        );

    }

    @Test
    void given_wrongPassword_thenThenLoginFailException() throws Exception {
        //given
        final String email = "boriPapa@gmail.com";
        final String username = "username";
        final String password = "password1234!@";
        final String nickname = "boriPapa";
        final String wrongPassword = "wrongPassword";
        userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();
        UserAccount userAccount = UserAccount.from(new UserAccountCreate(email, username, password, nickname), userAccountPasswordEncoder);
        UserAccountLogin userAccountLogin = new UserAccountLogin(wrongPassword);
        //when
        AbstractThrowableAssert<?, ? extends Throwable> thrown = assertThatThrownBy(() -> userAccount.login(userAccountLogin, userAccountPasswordEncoder));
        //then
        thrown.isInstanceOf(LoginFailException.class);


    }

    @Test
    void givenPasswordAndUserAccountPasswordEncoder_thenPossibleToLogin() throws Exception {
        //given
        final String email = "boriPapa@gmail.com";
        final String username = "username";
        final String password = "password1234!@";
        final String nickname = "boriPapa";
        userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();
        UserAccount userAccount = UserAccount.from(new UserAccountCreate(email, username, password, nickname), userAccountPasswordEncoder);
        UserAccountLogin userAccountLogin = new UserAccountLogin(password);
        userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();
        //when
        UserAccount loggedInAccount = userAccount.login(userAccountLogin, userAccountPasswordEncoder);
        //then
        assertThat(loggedInAccount).isNotNull();
        assertThat(loggedInAccount.getLastLoginAt()).isNotNull();

    }

}