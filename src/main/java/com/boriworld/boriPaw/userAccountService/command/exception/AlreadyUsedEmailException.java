package com.boriworld.boriPaw.userAccountService.command.exception;

public class AlreadyUsedEmailException extends RuntimeException {
    public AlreadyUsedEmailException(String message) {
        super(message);
    }
}
