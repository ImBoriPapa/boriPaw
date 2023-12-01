package com.boriworld.boriPaw.accountService.command.infrastructure.validation;

import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountValidator;
import com.boriworld.boriPaw.accountService.command.exception.AlreadyUsedAccountNameException;
import com.boriworld.boriPaw.accountService.command.exception.AlreadyUsedEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AccountValidatorImpl implements AccountValidator {

    private final AccountRepository accountRepository;

    @Override
    public void validateDuplicateEmail(String email) {
        boolean exists = accountRepository.existsByEmail(email);

        if (exists) {
            throw new AlreadyUsedEmailException("이미 사용중인 이메일입니다.");
        }
    }

    @Override
    public void validateDuplicateAccountName(String accountName) {
        boolean exists = accountRepository.existsByAccountName(accountName);

        if (exists) {
            throw new AlreadyUsedAccountNameException("이미 사용중인 계정명입니다.");
        }
    }
}
