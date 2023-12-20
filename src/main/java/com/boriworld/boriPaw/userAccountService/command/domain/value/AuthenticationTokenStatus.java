package com.boriworld.boriPaw.userAccountService.command.domain.value;

import com.boriworld.boriPaw.userAccountService.command.domain.exception.AuthenticationTokenException;

public enum AuthenticationTokenStatus {
    ACCESS("no problem"),
    DENIED("this token is denied"),
    EXPIRED("this token is expired"),
    ERROR("this token is has error");

    private final String errorMessage;

    AuthenticationTokenStatus(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void throwAccessTokenErrorException() {
        if (this != ACCESS) {
            throw new AuthenticationTokenException(this.errorMessage);
        }
    }
}
