package com.boriworld.boriPaw.common.validator;

public interface RequestBodyFieldsConstraintValidator {
    /**
     * 주어진 객체의 제약 조건을 검증합니다.
     *
     * @param o 검증 대상 객체
     */
    void validate(Object o);
}
