package com.boriworld.boriPaw.userAccountService.command.domain.value;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.AuthenticationTokenException;

public enum AuthenticationTokenStatus {
    ACCESS("no problem", ProblemDefinition.NOT_FOUND_PROBLEM),
    DENIED("this token is denied", ProblemDefinition.DENIED_TOKEN),
    EXPIRED("this token is expired", ProblemDefinition.EXPIRED_TOKEN),
    ERROR("this token is has error", ProblemDefinition.ERROR_TOKEN);

    private final String errorMessage;
    private final ProblemDefinition problemDefinition;

    AuthenticationTokenStatus(String errorMessage, ProblemDefinition problemDefinition) {
        this.errorMessage = errorMessage;
        this.problemDefinition = problemDefinition;
    }

    public void throwAuthenticationTokenErrorException() {
        if (this != ACCESS) {
            throw AuthenticationTokenException.forMessage(this.errorMessage, this.problemDefinition);
        }
    }
}
