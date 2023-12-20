package com.boriworld.boriPaw.common.validator;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;

public final class ConstraintValidationFailureException extends CustomRuntimeException {

    private ConstraintValidationFailureException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static ConstraintValidationFailureException forMessage(final String message) {
        return new ConstraintValidationFailureException(message, ProblemDefinition.CONSTRAINT_VALIDATION_FAIL);
    }
}
