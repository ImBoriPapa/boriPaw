package com.boriworld.boriPaw.common.validator;

public interface RequestConstraintValidator<T> {
    /**
     * 주어진 객체의 제약 조건을 검증합니다.
     *
     * @param t 검증 대상 객체
     */
    void validate(T t);
}
