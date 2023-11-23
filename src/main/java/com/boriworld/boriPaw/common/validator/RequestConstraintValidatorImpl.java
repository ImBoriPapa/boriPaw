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

    /**
     * 주어진 객체의 제약 조건을 검증합니다.
     *
     * @param t 검증 대상 객체
     * @throws CustomValidationFailException 제약 조건 검증 실패 시 발생하는 예외
     */
    public void validate(T t) {

        checkTargetIsNotNull(t);

        Set<String> errorMessages = extractConstraintErrorMessages(t);

        final String objectName = t.getClass().getSimpleName();

        handleConstraintErrorMessages(objectName, errorMessages);
    }

    private void handleConstraintErrorMessages(String objectName, Set<String> errorMessages) {
        if (!errorMessages.isEmpty()) {
            log.error("Constraint validate failed for Object: {}", objectName);
            throw new CustomValidationFailException(String.join("\n", errorMessages));
        }
    }

    private void checkTargetIsNotNull(T t) {
        if (t == null) {
            log.error("fail constraint validate because for there is no for Object");
            throw new CustomValidationFailException("There is no Object for validate");
        }
    }

    private Set<String> extractConstraintErrorMessages(T t) {
        log.info("do constraint validate target for Object: {}", t.getClass().getSimpleName());
        return validator
                .validate(t)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }
}
