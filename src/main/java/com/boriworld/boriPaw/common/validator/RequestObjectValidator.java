package com.boriworld.boriPaw.common.validator;

public interface RequestObjectValidator<T> {
    void validate(T t);
}
