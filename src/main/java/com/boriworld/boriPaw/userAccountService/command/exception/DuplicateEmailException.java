package com.boriworld.boriPaw.userAccountService.command.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;

public final class DuplicateEmailException extends CustomRuntimeException {
    private DuplicateEmailException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static DuplicateEmailException forMessage(final String message) {
        return new DuplicateEmailException(message,ProblemDefinition.DUPLICATE_EMAIL);
    }
}
