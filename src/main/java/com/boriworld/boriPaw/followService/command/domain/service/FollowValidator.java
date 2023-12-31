package com.boriworld.boriPaw.followService.command.domain.service;

public interface FollowValidator {
    boolean support(Class<?> clazz);
    void validate(Object o);
}
