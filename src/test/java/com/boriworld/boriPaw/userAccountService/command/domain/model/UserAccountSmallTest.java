package com.boriworld.boriPaw.userAccountService.command.domain.model;

import com.boriworld.boriPaw.userAccountService.command.domain.exception.LoginFailException;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccountLogin;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.value.*;
import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeUserAccountPasswordEncoder;
import org.assertj.core.api.AbstractThrowableAssert;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccountTestHelper.getUserAccount;
import static com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccountTestHelper.getUserAccountCreate;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserAccountSmallTest {
    private UserAccountPasswordEncoder userAccountPasswordEncoder;

    @BeforeEach
    void beforeEach() {
        this.userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();
    }

    @Test
    @DisplayName(value = "UserAccountCreate 객체가 없으면 NullPoint 예외 발생")
    void givenNullAccountCreate_thenThrowNullPointException() throws Exception {
        //given
        UserAccountPasswordEncoder userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();
        //when
        //then
        assertThatThrownBy(() -> UserAccount.from(null, userAccountPasswordEncoder))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName(value = "AccountPasswordEncoder 객체가 Null 이면 NullPointException")
    void givenNullAccountPasswordEncoder_thenThrowNullPointException() throws Exception {
        //given
        UserAccountCreate userAccountCreate = getUserAccountCreate();
        //when
        //then
        assertThatThrownBy(() -> UserAccount.from(userAccountCreate, null))
                .isInstanceOf(NullPointerException.class);
    }


    @Test
    @DisplayName(value = "UserAccountCreate 와 UserAccountPasswordEncoder 로 UserAccount 를 만들수 있다.")
    void givenUserAccountCreateAndUserAccountPasswordEncoder_thenCreateUserAccount() throws Exception {
        //given
        UserAccountCreate userAccountCreate = getUserAccountCreate();
        userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();
        //when
        UserAccount userAccount = UserAccount.from(userAccountCreate, userAccountPasswordEncoder);
        //then
        assertAll(
                () -> assertThat(userAccount.getUserAccountId()).isNull(),
                () -> assertThat(userAccount.getEmail()).isEqualTo(userAccountCreate.email()),

                () -> assertThat(userAccount.getPassword()).isNotEqualTo(userAccountCreate.password()),
//                () -> assertThat(userAccount.getUserProfile().getNickname()).isEqualTo(userAccountCreate.nickname()),
//                () -> assertThat(userAccount.getUserProfile().getProfileImage()).isNull(),
                () -> assertThat(userAccount.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE),
                () -> assertThat(userAccount.getPasswordStatus()).isEqualTo(PasswordStatus.STEADY),
                () -> assertThat(userAccount.getAuthority()).isEqualTo(Authority.USER),
                () -> assertThat(userAccount.getCreatedAt()).isNotNull(),
                () -> assertThat(userAccount.getUpdatedAt()).isNull(),
                () -> assertThat(userAccount.getLastLoginAt()).isNull()
        );
    }

    @Test
    @DisplayName(value = "UserAccountInitialize 로 UserAccount 를 초기화 할 수 있다.")
    void givenUserAccountInitialize_thenInitializeUserAccount() throws Exception {
        //given
        final UserAccountId userAccountId = UserAccountId.of(123L);
        final String email = "boriPapa@gmail.com";
        final String accountName = "username";
        final String password = "password1234!@";
        final String nickname = "boriPapa";
        final String profileImage = "imageurl";
        final String introduce = "안녕하세요";
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
                () -> assertThat(userAccount.getAccountStatus()).isEqualTo(accountStatus),
                () -> assertThat(userAccount.getPasswordStatus()).isEqualTo(passwordStatus),
                () -> assertThat(userAccount.getAuthority()).isEqualTo(authority),
                () -> assertThat(userAccount.getCreatedAt()).isEqualTo(createdAt),
                () -> assertThat(userAccount.getUpdatedAt()).isEqualTo(updatedAt),
                () -> assertThat(userAccount.getLastLoginAt()).isEqualTo(lastLoginAt)
        );

    }

    @Test
    @DisplayName(value = "잚못된 비밀번호로 로그인 시도시 LoginFailException")
    void givenWrongPassword_thenThrowLoginFailException() throws Exception {
        //given
        final String WRONG_PASSWORD = "wrongPassword";
        final UserAccountLogin userAccountLogin = new UserAccountLogin(WRONG_PASSWORD);
        //when
        //then
        assertThatThrownBy(() -> getUserAccount(userAccountPasswordEncoder)
                .login(userAccountLogin, userAccountPasswordEncoder))
                .isInstanceOf(LoginFailException.class);
    }

    @Test
    @DisplayName(value = "비밀번호와 UserAccountPasswordEncoder 로 로그인을 할 수 있다.")
    void givenPasswordAndUserAccountPasswordEncoder_thenPossibleToLogin() throws Exception {
        //given
        final String PASSWORD = "password1234!@";
        UserAccountLogin userAccountLogin = new UserAccountLogin(PASSWORD);
        //when
        UserAccount loggedInAccount = getUserAccount(PASSWORD, userAccountPasswordEncoder)
                .login(userAccountLogin, userAccountPasswordEncoder);
        //then
        assertThat(loggedInAccount).isNotNull();
        assertThat(loggedInAccount.getLastLoginAt()).isNotNull();

    }

}