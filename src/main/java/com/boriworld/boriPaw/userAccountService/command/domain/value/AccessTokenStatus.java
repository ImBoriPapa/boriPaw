package com.boriworld.boriPaw.userAccountService.command.domain.value;

import com.boriworld.boriPaw.userAccountService.command.exception.AccessTokenAuthenticationException;
/**
 * Value Object
 */
public enum AccessTokenStatus {
    ACCESS("no problem"),
    DENIED("this token is denied"),
    EXPIRED("this token is expired"),
    ERROR("this token is has error");

    private final String detail;

    AccessTokenStatus(String detail) {
        this.detail = detail;
    }

    public void throwAccessTokenErrorException() {
        if (this != ACCESS) {
            throw new AccessTokenAuthenticationException(this.detail);
        }
    }
}
