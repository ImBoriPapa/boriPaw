package com.boriworld.boriPaw.common.validator;


import jakarta.validation.ConstraintViolation;

import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestObjectValidatorImpl<T> implements RequestObjectValidator<T> {
    private final Validator validator;

    public void validate(T t) {
        Set<String> errorMessages = extractConstraintErrorMessages(t);

        if (!errorMessages.isEmpty()) {
            throw new CustomValidationException(String.join("\n", errorMessages));
        }
    }

    private Set<String> extractConstraintErrorMessages(T t) {
        return validator
                .validate(t)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }
}
