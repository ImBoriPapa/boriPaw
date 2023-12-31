package com.boriworld.boriPaw.followService.command.domain.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;

public final class AlreadyFollowException extends CustomRuntimeException {
    private AlreadyFollowException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static AlreadyFollowException forMessage(final String message) {
        return new AlreadyFollowException(message, ProblemDefinition.ALREADY_FOLLOW);
    }
}
