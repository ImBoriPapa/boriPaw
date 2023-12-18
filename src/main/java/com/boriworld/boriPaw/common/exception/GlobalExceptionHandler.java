package com.boriworld.boriPaw.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handle(Exception e) {
        log.error("occur unhandled exception message: {}, stackTrace: {}", e.getMessage(), e.getStackTrace());
        ProblemDetail detail = ProblemDetail.forStatus(400);
        detail.setDetail(e.getLocalizedMessage());
        return detail;
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ProblemDetail handle(CustomRuntimeException e) {
        log.error("handle 'CustomRuntimeException' exception class name is: {}, message: {}", e.getClass().getSimpleName(), e.getMessage());
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
        detail.setType(createTypeURI(e));
        detail.setTitle(e.getTitle());
        return detail;
    }
    private URI createTypeURI(CustomRuntimeException e) {

        return UriComponentsBuilder
                .newInstance()
                .host("localhost")
                .port(8080)
                .path(e.getType())
                .build()
                .toUri();
    }
}
