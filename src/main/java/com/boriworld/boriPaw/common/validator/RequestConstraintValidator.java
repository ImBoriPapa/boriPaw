package com.boriworld.boriPaw.common.validator;

public interface RequestConstraintValidator<T> {
    void validate(T t);
}
