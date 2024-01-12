package com.boriworld.boriPaw.userAccountService.command.domain.model;

import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import lombok.Getter;

@Getter
public class UserAccountTestHelper {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String NICKNAME = "nickname";

    public static UserAccountCreate getUserAccountCreate() {
        return new UserAccountCreate(EMAIL, PASSWORD, NICKNAME);
    }


    public static UserAccount getUserAccount(UserAccountPasswordEncoder encoder) {
        return UserAccount.from(getUserAccountCreate(), encoder);
    }


    public static UserAccount getUserAccount(String password, UserAccountPasswordEncoder encoder) {
        return UserAccount.from(new UserAccountCreate(EMAIL, password, NICKNAME), encoder);
    }
}
