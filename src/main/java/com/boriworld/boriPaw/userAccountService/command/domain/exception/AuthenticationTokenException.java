package com.boriworld.boriPaw.userAccountService.command.domain.exception;


import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public final class AuthenticationTokenException extends AuthenticationException {
    private String type;
    private String title;
    private HttpStatus status;

    private AuthenticationTokenException(String msg) {
        super(msg);
    }

    private AuthenticationTokenException(String msg, ProblemDefinition problemDefinition) {
        super(msg);
        this.type = problemDefinition.getType();
        this.title = problemDefinition.getTitle();
        this.status = problemDefinition.getStatus();
    }

    public static AuthenticationTokenException forMessage(final String message, ProblemDefinition problemDefinition) {
        return new AuthenticationTokenException(message, problemDefinition);
    }
}
