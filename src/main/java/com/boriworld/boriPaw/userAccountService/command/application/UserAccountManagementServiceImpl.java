package com.boriworld.boriPaw.userAccountService.command.application;


import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountValidator;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.event.AccountCreateEvent;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.event.UserAccountEventPublisher;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountManagementServiceImpl implements UserAccountManagementService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountPasswordEncoder userAccountPasswordEncoder;
    private final UserAccountEventPublisher userAccountEventPublisher;
    private final Set<UserAccountValidator> validators;

    @Transactional
    public UserAccountId processUserAccountCreation(UserAccountCreate userAccountCreate) {
        log.info("start user account creation process.");
        validate(userAccountCreate);
        UserAccount userAccount = saveAccount(userAccountCreate);
        userAccountEventPublisher.publish(AccountCreateEvent.of(userAccount));
        return userAccount.getUserAccountId();
    }

    private UserAccount saveAccount(UserAccountCreate userAccountCreate) {
        return userAccountRepository.save(UserAccount.from(userAccountCreate, userAccountPasswordEncoder));
    }

    private void validate(Object object) {
        validators.stream().filter(o -> o.supports(object.getClass()))
                .findFirst()
                .orElseThrow(() -> UnableToFindSupportedValidatorException.forMessage("Unable to find a supportable validator"))
                .validate(object);
    }

}

