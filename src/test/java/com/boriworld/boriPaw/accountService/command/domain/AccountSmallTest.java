package com.boriworld.boriPaw.accountService.command.domain;

import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountStatus;
import com.boriworld.boriPaw.accountService.command.domain.value.Authority;
import com.boriworld.boriPaw.accountService.command.domain.value.PasswordStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AccountSmallTest {

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
    void AccountCreate_를_AccountPasswordEncoder_를_사용해서_Account_객체_생성() throws Exception {
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

}