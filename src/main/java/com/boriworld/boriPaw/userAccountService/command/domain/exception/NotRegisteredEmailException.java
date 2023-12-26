package com.boriworld.boriPaw.userAccountService.command.domain.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;

public final class NotRegisteredEmailException extends CustomRuntimeException {
    private NotRegisteredEmailException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static NotRegisteredEmailException forMessage(final String message) {
        return new NotRegisteredEmailException(message, ProblemDefinition.NOT_REGISTERED_EMAIL);
    }
}
