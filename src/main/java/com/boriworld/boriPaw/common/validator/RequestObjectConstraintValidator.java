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
public class RequestObjectConstraintValidator implements RequestConstraintValidator {
    private final Validator validator;

    /**
     * 주어진 객체의 제약 조건을 검증합니다.
     *
     * @param o 검증 대상 객체
     * @throws ConstraintValidationFailureException 제약 조건 검증 실패 시 발생하는 예외
     */
    public void validate(Object o) {

        checkTargetIsNotNull(o);

        Set<String> errorMessages = extractConstraintErrorMessages(o);

        final String objectName = o.getClass().getSimpleName();

        handleConstraintErrorMessages(objectName, errorMessages);
    }

    private void handleConstraintErrorMessages(String objectName, Set<String> errorMessages) {
        if (!errorMessages.isEmpty()) {
            String errorMessage = String.format("Object: %s", objectName) + " Message:" + String.join(", ", errorMessages);
            throw ConstraintValidationFailureException.forMessage(errorMessage);
        }
    }


    private void checkTargetIsNotNull(Object o) {
        if (o == null) {
            throw ConstraintValidationFailureException.forMessage("There is no Object for validate");
        }
    }

    private Set<String> extractConstraintErrorMessages(Object o) {
        return validator
                .validate(o)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }
}
