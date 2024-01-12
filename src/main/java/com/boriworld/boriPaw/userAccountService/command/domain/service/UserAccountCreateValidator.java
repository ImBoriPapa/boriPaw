package com.boriworld.boriPaw.userAccountService.command.domain.service;

import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.DuplicateEmailException;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.DuplicateUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserAccountCreateValidator implements UserAccountValidator {
    private final UserAccountRepository userAccountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserAccountCreate.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object) {
        UserAccountCreate userAccountCreate = (UserAccountCreate) object;
        checkDuplicateEmailCheck(userAccountCreate);
    }

    private void checkDuplicateEmailCheck(UserAccountCreate userAccountCreate) {
        boolean existsByEmail = userAccountRepository.existsByEmail(userAccountCreate.email());

        if (existsByEmail) {
            throw DuplicateEmailException.forMessage(String.format("The email address '%s' is already registered.", userAccountCreate.email()));
        }
    }
}
