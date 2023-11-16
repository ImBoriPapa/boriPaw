package com.boriworld.boriPaw.accountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.accountService.command.domain.Account;
import com.boriworld.boriPaw.accountService.command.domain.AccountPasswordEncoder;
import com.boriworld.boriPaw.accountService.command.domain.FakeAccountPasswordEncoder;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.testConfig.MySQLTestContainerRun;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class AccountRepositoryImplMediumTest extends MySQLTestContainerRun {
    @Autowired
    private AccountRepository accountRepository;
    private AccountPasswordEncoder passwordEncoder;

    @Test
    void test() throws Exception {
        //given
        final String email = "boriPapa@gmail.com";
        final String accountName = "accountName";
        final String password = "password1234!@";
        final String nickname = "boriPapa";
        passwordEncoder = new FakeAccountPasswordEncoder();
        AccountCreate create = new AccountCreate(email, accountName, nickname, password);
        Account account = Account.from(create, passwordEncoder);

        //when
        Account saved = accountRepository.save(account);
        Account findById = accountRepository.findById(saved.getAccountId())
                .orElseThrow();
        //then
        assertThat(saved.getAccountId()).isNotNull();
        assertThat(findById.getAccountId()).isEqualTo(saved.getAccountId());


    }

}