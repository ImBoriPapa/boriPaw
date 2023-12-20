package com.boriworld.boriPaw.userAccountService.command.domain.exception;

public class TokenReissueFailException extends RuntimeException {
    public TokenReissueFailException(String message) {
        super(message);
    }
}
