package com.boriworld.boriPaw.common.exception;


import com.boriworld.boriPaw.common.factory.ErrorResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final ErrorResponseFactory factory;

    @ExceptionHandler(Exception.class)
    public ProblemDetail handle(Exception e) {
        log.error("occur unhandled exception message: {}, stackTrace: {}", e.getMessage(), e.getStackTrace());
        return factory.problemDetail(e);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ProblemDetail handle(CustomRuntimeException e) {
        log.error("handle 'CustomRuntimeException' exception class name is: {}, message: {}", e.getClass().getSimpleName(), e.getMessage());
        return factory.problemDetail(e);
    }

}
