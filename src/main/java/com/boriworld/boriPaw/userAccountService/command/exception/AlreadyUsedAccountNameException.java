package com.boriworld.boriPaw.userAccountService.command.exception;

public class AlreadyUsedAccountNameException extends RuntimeException {
    private final String message;
    public AlreadyUsedAccountNameException(String message) {
        this.message = message;
    }
}
