package com.boriworld.boriPaw.accountService.command.domain.exception;

public class AlreadyUsedEmailException extends RuntimeException {
    public AlreadyUsedEmailException(String message) {
        super(message);
    }
}
