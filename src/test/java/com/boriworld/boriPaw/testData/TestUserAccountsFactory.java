package com.boriworld.boriPaw.testData;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserProfileRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserProfileCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Component
@ActiveProfiles(value = {"test", "local"})
@RequiredArgsConstructor
@Transactional
public class TestUserAccountsFactory {
    private final UserAccountPasswordEncoder encoder;
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final EntityManager em;
    public final String TESTER_EMAIL = "tester@gmail.com";
    public final String TESTER_RAW_PASSWORD = "password1234!@";
    public final String TESTER_NICKNAME = "tester";

    public UserAccount initTester() {
        UserAccountCreate accountCreate = new UserAccountCreate(TESTER_EMAIL, TESTER_RAW_PASSWORD, TESTER_NICKNAME);
        UserAccount userAccount = userAccountRepository.save(UserAccount.from(accountCreate, encoder));
        UserProfile userProfile = UserProfile.of(new UserProfileCreate(userAccount, TESTER_NICKNAME));
        userProfileRepository.save(userProfile);
        em.flush();
        em.clear();
        return userAccount;
    }
}
