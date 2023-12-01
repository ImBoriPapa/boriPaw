package com.boriworld.boriPaw.accountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.accountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountPasswordEncoder;
import com.boriworld.boriPaw.testComponent.fakeComponents.FakeAccountPasswordEncoder;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.testContainer.testcontainer.MySQLTestContainerRunner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.assertj.core.api.Assertions.*;

class UserAccountRepositoryImplMediumTest extends MySQLTestContainerRunner {
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
        UserAccount userAccount = UserAccount.from(create, passwordEncoder);

        //when
        UserAccount saved = accountRepository.save(userAccount);
        UserAccount findById = accountRepository.findById(saved.getAccountId())
                .orElseThrow();
        //then
        assertThat(saved.getAccountId()).isNotNull();
        assertThat(findById.getAccountId()).isEqualTo(saved.getAccountId());
    }

}