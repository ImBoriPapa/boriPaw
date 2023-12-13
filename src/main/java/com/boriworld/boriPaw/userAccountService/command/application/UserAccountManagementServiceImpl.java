package com.boriworld.boriPaw.userAccountService.command.application;

import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.event.AccountCreateEvent;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.event.UserAccountEventPublisher;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountValidator;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountManagementServiceImpl implements UserAccountManagementService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountPasswordEncoder userAccountPasswordEncoder;
    private final UserAccountEventPublisher userAccountEventPublisher;
    private final RequestConstraintValidator<UserAccountCreate> requestConstraintValidator;
    private final UserAccountValidator userAccountValidator;

    @Transactional
    public UserAccountId processUserAccountCreation(UserAccountCreate userAccountCreate) {
        log.info("Start user account creation process.");
        validateRequestedData(userAccountCreate);

        UserAccount savedUserAccount = saveAccount(userAccountCreate);

        publishCreateEvent(savedUserAccount);
        log.info("Finish user account creation process successfully. AccountId: {}", savedUserAccount.getUserAccountId().getId());
        return savedUserAccount.getUserAccountId();

    }

    private UserAccount saveAccount(UserAccountCreate userAccountCreate) {
        return userAccountRepository.save(UserAccount.from(userAccountCreate, userAccountPasswordEncoder));
    }

    private void validateRequestedData(UserAccountCreate userAccountCreate) {
        requestConstraintValidator.validate(userAccountCreate);

        userAccountValidator.validateDuplicateEmail(userAccountCreate.email());

        userAccountValidator.validateDuplicateUserName(userAccountCreate.userName());
    }

    private void publishCreateEvent(UserAccount savedUserAccount) {
        userAccountEventPublisher.publish(new AccountCreateEvent(savedUserAccount.getUserAccountId(), savedUserAccount.getEmail()));
    }
}

