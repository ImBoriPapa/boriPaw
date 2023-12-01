package com.boriworld.boriPaw.accountService.command.domain.service;

public interface AccountValidator {
    void validateDuplicateEmail(String email);
    void validateDuplicateAccountName(String accountName);
}
