package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.FakeUserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.testContainer.testcontainer.MySQLTestContainerRunner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.assertj.core.api.Assertions.*;

class UserUserAccountRepositoryImplMediumTest extends MySQLTestContainerRunner {
    @Autowired
    private UserAccountRepository userAccountRepository;
    private UserAccountPasswordEncoder passwordEncoder;

    @Test
    void test() throws Exception {
        //given
        final String email = "boriPapa@gmail.com";
        final String accountName = "accountName";
        final String password = "password1234!@";
        final String nickname = "boriPapa";
        passwordEncoder = new FakeUserAccountPasswordEncoder();
        UserAccountCreate create = new UserAccountCreate(email, accountName, nickname, password);
        UserAccount userAccount = UserAccount.from(create, passwordEncoder);

        //when
        UserAccount saved = userAccountRepository.save(userAccount);
        UserAccount findById = userAccountRepository.findById(saved.getUserAccountId())
                .orElseThrow();
        //then
        assertThat(saved.getUserAccountId()).isNotNull();
        assertThat(findById.getUserAccountId()).isEqualTo(saved.getUserAccountId());
    }

}