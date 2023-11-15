package com.boriworld.boriPaw.accountService.command.domain.exception;

public class AlreadyUsedAccountNameException extends RuntimeException {
    private final String message;
    public AlreadyUsedAccountNameException(String message) {
        this.message = message;
    }
}
