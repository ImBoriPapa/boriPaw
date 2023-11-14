package com.boriworld.boriPaw.accountService.command.domain.exception;

public class AlreadyUsedEmailException extends RuntimeException {
    private final String message;
    public AlreadyUsedEmailException(String message) {
        this.message = message;
    }
}
