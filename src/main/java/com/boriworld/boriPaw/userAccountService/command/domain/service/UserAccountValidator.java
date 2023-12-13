package com.boriworld.boriPaw.userAccountService.command.domain.service;

public interface UserAccountValidator {
    void validateDuplicateEmail(String email);
    void validateDuplicateUserName(String accountName);
}
