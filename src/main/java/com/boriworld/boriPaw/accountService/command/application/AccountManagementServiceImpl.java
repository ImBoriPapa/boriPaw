package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.event.AccountCreateEvent;
import com.boriworld.boriPaw.accountService.command.domain.model.Account;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountEventPublisher;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountPasswordEncoder;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountValidator;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountManagementServiceImpl implements AccountManagementService {
    private final AccountRepository accountRepository;
    private final AccountPasswordEncoder accountPasswordEncoder;
    private final AccountEventPublisher accountEventPublisher;
    private final RequestConstraintValidator<AccountCreate> requestConstraintValidator;
    private final AccountValidator accountValidator;

    @Transactional
    public AccountId processAccountCreation(AccountCreate accountCreate) {
        log.info("Start account creation process.");
        validateRequestedData(accountCreate);

        Account savedAccount = saveAccount(accountCreate);

        publishCreateEvent(savedAccount);
        log.info("Finish account creation process successfully. AccountId: {}", savedAccount.getAccountId().getId());
        return savedAccount.getAccountId();

    }

    private Account saveAccount(AccountCreate accountCreate) {
        return accountRepository.save(Account.from(accountCreate, accountPasswordEncoder));
    }

    private void validateRequestedData(AccountCreate accountCreate) {
        requestConstraintValidator.validate(accountCreate);

        accountValidator.validateDuplicateEmail(accountCreate.email());

        accountValidator.validateDuplicateAccountName(accountCreate.accountName());
    }

    private void publishCreateEvent(Account savedAccount) {
        accountEventPublisher.publish(new AccountCreateEvent(savedAccount.getAccountId(), savedAccount.getEmail()));
    }
}

