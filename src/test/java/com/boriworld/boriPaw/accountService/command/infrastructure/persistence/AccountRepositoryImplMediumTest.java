package com.boriworld.boriPaw.accountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.accountService.command.domain.model.Account;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountPasswordEncoder;
import com.boriworld.boriPaw.testContanier.FakeAccountPasswordEncoder;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.testConfig.MySQLTestContainerRuner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.assertj.core.api.Assertions.*;

class AccountRepositoryImplMediumTest extends MySQLTestContainerRuner {
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