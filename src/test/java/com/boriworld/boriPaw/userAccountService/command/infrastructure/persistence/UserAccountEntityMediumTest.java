package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.testContainer.testcontainer.MediumTestContainerRunner;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccountTestHelper;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.infrastructure.imports.UserAccountPasswordEncoderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserAccountEntityMediumTest extends MediumTestContainerRunner {

    private UserAccountPasswordEncoder userAccountPasswordEncoder;
    private UserAccountRepository userAccountRepository;
    @BeforeEach
    void beforeEach() {
        userAccountPasswordEncoder = new UserAccountPasswordEncoderImpl();
    }

    @Test
    @DisplayName("test")
    void test1() throws Exception{
        //given
        UserAccount userAccount = UserAccountTestHelper.getUserAccount(userAccountPasswordEncoder);
        UserAccountEntity userAccountEntity = UserAccountEntity.fromModel(userAccount);
        //when
        UserAccount account = userAccountRepository.save(userAccountEntity.toModel());
        //then

    }

}