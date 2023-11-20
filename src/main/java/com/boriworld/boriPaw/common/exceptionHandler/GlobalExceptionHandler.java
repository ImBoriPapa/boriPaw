package com.boriworld.boriPaw.common.exceptionHandler;

import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handle(ValidationException exception) {

        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }
}
