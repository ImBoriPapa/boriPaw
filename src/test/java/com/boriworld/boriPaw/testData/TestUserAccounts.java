package com.boriworld.boriPaw.testData;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@ActiveProfiles(value = {"test", "local"})
@RequiredArgsConstructor
@Transactional
public class TestUserAccounts {
    private final UserAccountPasswordEncoder encoder;
    private final UserAccountRepository userAccountRepository;
    private final EntityManager em;

    public List<UserAccount> init() {
        List<UserAccount> userAccounts = new ArrayList<>();
        String email = "tester1@test.com";
        String username = "testUser1";
        String password = "password1234!@";
        String nickname = "tester1";

        UserAccountCreate create = new UserAccountCreate(email, username, password, nickname);
        UserAccount userAccount = UserAccount.from(create, encoder);
        UserAccount account = userAccountRepository.save(userAccount);
        userAccounts.add(account);
        return userAccounts;
    }
}
