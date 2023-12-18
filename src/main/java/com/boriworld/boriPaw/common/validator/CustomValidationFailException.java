package com.boriworld.boriPaw.common.validator;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;

public final class CustomValidationFailException extends CustomRuntimeException {

    private CustomValidationFailException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static CustomValidationFailException forMessage(final String message) {
        return new CustomValidationFailException(message, ProblemDefinition.CONSTRAINT_VALIDATION_FAIL);
    }
}
