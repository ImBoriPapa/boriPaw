package com.boriworld.boriPaw.userAccountService.command.exception;

public class UserAccountMismatchException extends RuntimeException {
    public UserAccountMismatchException(String message) {
        super(message);
    }
}
