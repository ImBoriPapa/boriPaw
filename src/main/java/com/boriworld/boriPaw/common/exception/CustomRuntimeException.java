package com.boriworld.boriPaw.common.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomRuntimeException extends RuntimeException {
    private final String type;
    private final String title;
    private final HttpStatus status;
    protected CustomRuntimeException(String message, ProblemDefinition problemDefinition) {
        super(message);
        this.type = problemDefinition.getType();
        this.title = problemDefinition.getTitle();
        this.status = problemDefinition.getStatus();
    }
}
