package com.boriworld.boriPaw.userAccountService.query.domain.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;

public class ResourceNotFoundException extends CustomRuntimeException {
    protected ResourceNotFoundException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static ResourceNotFoundException forMessage(final String message) {
        return new ResourceNotFoundException(message, ProblemDefinition.NOT_FOUND_RESOURCE);
    }
}
