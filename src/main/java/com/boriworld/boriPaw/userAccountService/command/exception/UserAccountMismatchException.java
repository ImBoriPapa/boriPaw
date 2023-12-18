package com.boriworld.boriPaw.userAccountService.command.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;

public final class UserAccountMismatchException extends CustomRuntimeException {
    private UserAccountMismatchException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static UserAccountMismatchException forMessage(final String message) {
        return new UserAccountMismatchException(message,ProblemDefinition.USER_ACCOUNT_MISMATCH);
    }
}
