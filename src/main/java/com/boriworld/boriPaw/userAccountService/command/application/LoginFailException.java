package com.boriworld.boriPaw.userAccountService.command.application;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginFailException extends RuntimeException {
    private HttpStatus status;

    public LoginFailException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
