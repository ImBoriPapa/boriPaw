package com.boriworld.boriPaw.userAccountService.command.application;

public class LoginFailException extends RuntimeException {
    public LoginFailException(String message) {
        super(message);
    }
}
