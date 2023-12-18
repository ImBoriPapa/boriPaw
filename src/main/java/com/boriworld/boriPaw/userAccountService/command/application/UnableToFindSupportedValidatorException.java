package com.boriworld.boriPaw.userAccountService.command.application;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;

public final class UnableToFindSupportedValidatorException extends CustomRuntimeException {
    private UnableToFindSupportedValidatorException(String message, ProblemDefinition definition) {
        super(message,definition);
    }

    public static UnableToFindSupportedValidatorException forMessage(final String message) {
        return new UnableToFindSupportedValidatorException(message, ProblemDefinition.UNABLE_FIND_SUPPORTABLE_VALIDATOR);
    }
}
