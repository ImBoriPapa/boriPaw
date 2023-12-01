package com.boriworld.boriPaw.testComponent.fakeComponents;

import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountValidator;
import com.boriworld.boriPaw.accountService.command.exception.AlreadyUsedAccountNameException;
import com.boriworld.boriPaw.accountService.command.exception.AlreadyUsedEmailException;

public class FakeAccountValidator implements AccountValidator {
    private final AccountRepository accountRepository;

    public FakeAccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void validateDuplicateEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new AlreadyUsedEmailException(email);
        }
    }

    @Override
    public void validateDuplicateAccountName(String accountName) {
        if (accountRepository.existsByAccountName(accountName)) {
            throw new AlreadyUsedAccountNameException(accountName);
        }
    }
}
