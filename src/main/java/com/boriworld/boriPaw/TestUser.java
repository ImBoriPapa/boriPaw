package com.boriworld.boriPaw;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserProfileRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserProfileCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


import java.util.stream.IntStream;

@Component
@Profile({"local","product"})
@RequiredArgsConstructor
@Slf4j
public class TestUser {
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;

    private final UserAccountPasswordEncoder userAccountPasswordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Init Tester Data");

        IntStream.rangeClosed(1, 10).forEach(index -> {
            String email = "tester" + index + "@gmail.com";
            String password = "123456";
            String nickname = "tester" + index;
            UserAccount userAccount = UserAccount.from(new UserAccountCreate(email, password, nickname), userAccountPasswordEncoder);
            UserAccount account = userAccountRepository.save(userAccount);
            UserProfileCreate profileCreate = new UserProfileCreate(account, nickname);
            UserProfile userProfile = UserProfile.of(profileCreate);
            UserProfile profile = userProfileRepository.save(userProfile);
        });

    }
}
