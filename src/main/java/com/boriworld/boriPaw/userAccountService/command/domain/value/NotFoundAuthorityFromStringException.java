package com.boriworld.boriPaw.userAccountService.command.domain.value;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;

public final class NotFoundAuthorityFromStringException extends CustomRuntimeException {
    private NotFoundAuthorityFromStringException(String message, ProblemDefinition problemDefinition) {
        super(message,problemDefinition);
    }

    public static NotFoundAuthorityFromStringException forMessage(final String message) {
        return new NotFoundAuthorityFromStringException(message, ProblemDefinition.NOT_FOUND_AUTHORITY_FROM_STRING);
    }
}
