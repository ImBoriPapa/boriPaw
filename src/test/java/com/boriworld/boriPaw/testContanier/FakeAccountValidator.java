package com.boriworld.boriPaw.accountService.command.interfaces;

import com.boriworld.boriPaw.accountService.command.domain.service.AccountValidator;

public class FakeAccountValidator implements AccountValidator {
    @Override
    public void validateDuplicateEmail(String email) {

    }

    @Override
    public void validateDuplicateAccountName(String accountName) {

    }
}
