package com.boriworld.boriPaw.userAccountService.command.exception;



import org.springframework.security.core.AuthenticationException;

public class AccessTokenAuthenticationException extends AuthenticationException {
    public AccessTokenAuthenticationException(String msg) {
        super(msg);
    }
}
