package com.boriworld.boriPaw.common.validator;


import jakarta.validation.ConstraintViolation;

import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestConstraintValidatorImpl<T> implements RequestConstraintValidator<T> {
    private final Validator validator;

    public void validate(T t) {
        Set<String> errorMessages = extractConstraintErrorMessages(t);

        if (!errorMessages.isEmpty()) {
            String requestObjectName = t.getClass().getSimpleName();
            log.error("Constraint validate failed for Object: {}", requestObjectName);
            throw new CustomValidationFailException(String.join("\n", errorMessages));
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
