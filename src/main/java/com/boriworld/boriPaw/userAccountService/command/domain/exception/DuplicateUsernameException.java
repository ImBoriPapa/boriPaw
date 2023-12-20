package com.boriworld.boriPaw.userAccountService.command.domain.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;


public final class DuplicateUsernameException extends CustomRuntimeException {
    public DuplicateUsernameException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static DuplicateUsernameException forMessage(final String message) {
        return new DuplicateUsernameException(message, ProblemDefinition.DUPLICATE_USERNAME);
    }
}
