package com.boriworld.boriPaw.accountService.command.domain.validator;

public class CustomValidationException extends RuntimeException {
    public CustomValidationException(String message) {
        super(message);
    }
}
