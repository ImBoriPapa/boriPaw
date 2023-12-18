package com.boriworld.boriPaw.userAccountService.command.interfaces;

public class TokenReissueFailException extends RuntimeException {
    public TokenReissueFailException(String message) {
        super(message);
    }
}
