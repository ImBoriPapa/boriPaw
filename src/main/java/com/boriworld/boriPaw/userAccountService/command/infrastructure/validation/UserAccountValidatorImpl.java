package com.boriworld.boriPaw.userAccountService.command.infrastructure.validation;

import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountValidator;
import com.boriworld.boriPaw.userAccountService.command.exception.AlreadyUsedAccountNameException;
import com.boriworld.boriPaw.userAccountService.command.exception.AlreadyUsedEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserAccountValidatorImpl implements UserAccountValidator {

    private final UserAccountRepository userAccountRepository;

    @Override
    public void validateDuplicateEmail(String email) {
        boolean exists = userAccountRepository.existsByEmail(email);

        if (exists) {
            throw new AlreadyUsedEmailException("이미 사용중인 이메일입니다.");
        }
    }

    @Override
    public void validateDuplicateAccountName(String accountName) {
        boolean exists = userAccountRepository.existsByAccountName(accountName);

        if (exists) {
            throw new AlreadyUsedAccountNameException("이미 사용중인 계정명입니다.");
        }
    }
}
