package com.boriworld.boriPaw.accountService.command.exception;

public class AlreadyUsedEmailException extends RuntimeException {
    public AlreadyUsedEmailException(String message) {
        super(message);
    }
}
