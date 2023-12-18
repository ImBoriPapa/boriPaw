package com.boriworld.boriPaw.userAccountService.command.domain;

import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.value.*;
import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeUserAccountPasswordEncoder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class UserAccountSmallTest {

    UserAccountPasswordEncoder userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();

    @Test
    void whenUserAccountCreateObjectIsNull_thenThrowNullPointException() throws Exception {
        //given

        //when

        //then
        assertThatThrownBy(() -> UserAccount.from(null, userAccountPasswordEncoder))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void whenUserAccountPasswordEncoderObjectIsNull_thenThrowNullPointException() throws Exception {
        //given
        final String email = "";
        final String username = "";
        final String password = "";
        final String nickname = "";
        UserAccountCreate create = new UserAccountCreate(email, username, password, nickname);
        //when

        //then
        assertThatThrownBy(() -> UserAccount.from(create, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void whenEmailIsNullThrow() throws Exception {
        //given
        final String email = "";
        final String username = "";
        final String password = "";
        final String nickname = "";

        UserAccountCreate userAccountCreate = new UserAccountCreate(email, username, password, nickname);
        //when

        //then
        assertThatThrownBy(() -> UserAccount.from(userAccountCreate, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void AccountCreate_와_AccountPasswordEncoder_를_사용해서_Account_객체_생성() throws Exception {
        //given
        final String email = "boriPapa@google.com";
        final String accountName = "boriPapaDa";
        final String password = "password1234!@";
        final String nickname = "boriPapa";

        UserAccountCreate userAccountCreate = new UserAccountCreate(email, accountName, password, nickname);

        //when
        UserAccount userAccount = UserAccount.from(userAccountCreate, userAccountPasswordEncoder);
        //then
        assertThat(userAccount.getUserAccountId()).isNull();
        assertThat(userAccount.getEmail()).isEqualTo(email);
        assertThat(userAccount.getUsername()).isEqualTo(accountName);
        assertThat(userAccount.getPassword()).isNotEqualTo(password);
        assertThat(userAccount.getUserProfile().getNickname()).isEqualTo(nickname);
        assertThat(userAccount.getUserProfile().getProfileImage()).isNull();
        assertThat(userAccount.getAccountStatus()).isEqualTo(AccountStatus.PENDING);
        assertThat(userAccount.getPasswordStatus()).isEqualTo(PasswordStatus.STEADY);
        assertThat(userAccount.getAuthority()).isEqualTo(Authority.USER);
        assertThat(userAccount.getCreatedAt()).isNotNull();
        assertThat(userAccount.getUpdatedAt()).isNull();
    }

    @Test
    void AccountInitialize_를_사용해서_Account_를_초기화할수있다() throws Exception {
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
        final LocalDateTime createdAt = LocalDateTime.of(2022, 12, 25, 17, 30);
        final LocalDateTime updatedAt = LocalDateTime.of(2023, 12, 25, 17, 30);


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
                .build();
        //when
        UserAccount userAccount = UserAccount.initialize(initialize);
        //then
        assertThat(userAccount).isNotNull();

    }

    @Test
    void password_UserAccountPasswordEncoder_을_사용해서_로그인할수있다() throws Exception {
        //given
        final String email = "boriPapa@gmail.com";
        final String accountName = "username";
        final String password = "password1234!@";
        final String nickname = "boriPapa";
        //when
        UserAccount userAccount = UserAccount.from(new UserAccountCreate(email, accountName, password, nickname), userAccountPasswordEncoder);
        UserAccount loggedInAccount = userAccount.login(password, userAccountPasswordEncoder);
        //then
        assertThat(loggedInAccount).isNotNull();
        assertThat(loggedInAccount.getLastLoginAt()).isAfter(userAccount.getLastLoginAt());

    }

}