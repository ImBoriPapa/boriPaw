package com.boriworld.boriPaw.accountService.command.application;

public class LoginFailException extends RuntimeException {
    public LoginFailException(String message) {
        super(message);
    }
}
