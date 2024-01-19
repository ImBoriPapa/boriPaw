package com.boriworld.boriPaw.common.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;

public class NoResourceUpdatePrivilegesException extends CustomRuntimeException{
    private NoResourceUpdatePrivilegesException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static NoResourceUpdatePrivilegesException forMessage(String message) {
        return new NoResourceUpdatePrivilegesException(message, ProblemDefinition.NO_RESOURCE_UPDATE_PRIVILEGES);
    }
}
