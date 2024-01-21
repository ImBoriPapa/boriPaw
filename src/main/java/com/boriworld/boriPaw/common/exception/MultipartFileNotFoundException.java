package com.boriworld.boriPaw.common.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;

public final class MultipartFileNotFoundException extends CustomRuntimeException{
    private MultipartFileNotFoundException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static MultipartFileNotFoundException forMessage(final String message) {
        return new MultipartFileNotFoundException(message,ProblemDefinition.NOT_FOUND_MULTIPART_FILE_NAME);
    }
}
