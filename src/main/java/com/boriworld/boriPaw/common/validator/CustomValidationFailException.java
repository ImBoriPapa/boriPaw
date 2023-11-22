package com.boriworld.boriPaw.common.validator;

public class CustomValidationFailException extends RuntimeException {
    public CustomValidationFailException(String message) {
        super(message);
    }
}
