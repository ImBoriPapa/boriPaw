package com.boriworld.boriPaw.userAccountService.command.interfaces.request;

import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountCreateRequest {
    private String email;
    private String username;
    private String password;
    private String nickname;

    public UserAccountCreate toAccountCreate() {
        return new UserAccountCreate(email, username, password, nickname);
    }
}
