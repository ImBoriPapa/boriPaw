package com.boriworld.boriPaw.accountService.command.application;

public interface AccountValidator {
    void validateDuplicateEmail(String email);
    void validateDuplicateAccountName(String accountName);
}
