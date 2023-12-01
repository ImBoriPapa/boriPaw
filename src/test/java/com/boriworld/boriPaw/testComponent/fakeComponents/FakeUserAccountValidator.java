package com.boriworld.boriPaw.testComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountValidator;
import com.boriworld.boriPaw.userAccountService.command.exception.AlreadyUsedAccountNameException;
import com.boriworld.boriPaw.userAccountService.command.exception.AlreadyUsedEmailException;

public class FakeUserAccountValidator implements UserAccountValidator {
    private final UserAccountRepository userAccountRepository;

    public FakeUserAccountValidator(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public void validateDuplicateEmail(String email) {
        if (userAccountRepository.existsByEmail(email)) {
            throw new AlreadyUsedEmailException(email);
        }
    }

    @Override
    public void validateDuplicateAccountName(String accountName) {
        if (userAccountRepository.existsByAccountName(accountName)) {
            throw new AlreadyUsedAccountNameException(accountName);
        }
    }
}
