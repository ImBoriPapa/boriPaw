package com.boriworld.boriPaw.accountService.command.domain;

import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountInitialize;
import com.boriworld.boriPaw.accountService.command.domain.model.Account;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountPasswordEncoder;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountStatus;
import com.boriworld.boriPaw.accountService.command.domain.value.Authority;
import com.boriworld.boriPaw.accountService.command.domain.value.PasswordStatus;
import com.boriworld.boriPaw.testComponent.fakeComponents.FakeAccountPasswordEncoder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class AccountSmallTest{

    AccountPasswordEncoder accountPasswordEncoder = new FakeAccountPasswordEncoder();
    @Test
    void AccountPasswordEncoder_가_Null_일때_NullPointerException_발생() throws Exception {
        //given

        //when

        //then
        assertThatThrownBy(() -> Account.from(null, accountPasswordEncoder))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    void AccountCreate_매개변수가_Null_일때_NullPointerException_발생() throws Exception {
        //given
        final String email = "boriPapa@google.com";
        final String accountName = "boriPapaDa";
        final String password = "password1234!@";
        final String nickname = "boriPapa";

        AccountCreate accountCreate = new AccountCreate(email, accountName, password, nickname);
        //when

        //then
        assertThatThrownBy(() -> Account.from(accountCreate, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void AccountCreate_와_AccountPasswordEncoder_를_사용해서_Account_객체_생성() throws Exception {
        //given
        final String email = "boriPapa@google.com";
        final String accountName = "boriPapaDa";
        final String password = "password1234!@";
        final String nickname = "boriPapa";

        AccountCreate accountCreate = new AccountCreate(email, accountName, password, nickname);

        //when
        Account account = Account.from(accountCreate, accountPasswordEncoder);
        //then
        assertThat(account.getAccountId()).isNull();
        assertThat(account.getEmail()).isEqualTo(email);
        assertThat(account.getAccountName()).isEqualTo(accountName);
        assertThat(account.getPassword()).isNotEqualTo(password);
        assertThat(account.getNickname()).isEqualTo(nickname);
        assertThat(account.getProfileImage()).isNull();
        assertThat(account.getAccountStatus()).isEqualTo(AccountStatus.PENDING);
        assertThat(account.getPasswordStatus()).isEqualTo(PasswordStatus.STEADY);
        assertThat(account.getAuthority()).isEqualTo(Authority.USER);
        assertThat(account.getCreatedAt()).isNotNull();
        assertThat(account.getUpdatedAt()).isNull();
    }

    @Test
    void AccountInitialize_를_사용해서_Account_를_초기화할수있다() throws Exception {
        //given
        final AccountId accountId = AccountId.of(123L);
        final String email = "boriPapa@gmail.com";
        final String accountName = "accountName";
        final String password = "password1234!@";
        final String nickname = "boriPapa";
        final String profileImage = "imageurl";
        final AccountStatus accountStatus = AccountStatus.ACTIVE;
        final PasswordStatus passwordStatus = PasswordStatus.STEADY;
        final Authority authority = Authority.USER;
        final LocalDateTime createdAt = LocalDateTime.of(2022, 12, 25, 17, 30);
        final LocalDateTime updatedAt = LocalDateTime.of(2023, 12, 25, 17, 30);


        AccountInitialize initialize = AccountInitialize.builder()
                .accountId(accountId)
                .email(email)
                .accountName(accountName)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .accountStatus(accountStatus)
                .passwordStatus(passwordStatus)
                .authority(authority)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        //when
        Account account = Account.initialize(initialize);
        //then
        assertThat(account).isNotNull();

    }

}