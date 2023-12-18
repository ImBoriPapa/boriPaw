package com.boriworld.boriPaw.userAccountService.command.domain.service;



public interface UserAccountValidator{
    boolean supports(Class<?> clazz);
    void validate(Object object);
}
