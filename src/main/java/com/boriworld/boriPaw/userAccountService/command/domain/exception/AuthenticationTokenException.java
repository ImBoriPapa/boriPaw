package com.boriworld.boriPaw.userAccountService.command.domain.exception;


import org.springframework.security.core.AuthenticationException;

public class AuthenticationTokenException extends AuthenticationException{
    public AuthenticationTokenException(String msg) {
        super(msg);
    }
}
