package com.boriworld.boriPaw.userAccountService.command.domain.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;


public final class LoginFailException extends CustomRuntimeException {
    private LoginFailException(String message, ProblemDefinition problemDefinition) {
        super(message,problemDefinition);
    }

    public static LoginFailException forMessage(final String message) {
        return new LoginFailException(message, ProblemDefinition.LOGIN_FAIL);
    }
}
