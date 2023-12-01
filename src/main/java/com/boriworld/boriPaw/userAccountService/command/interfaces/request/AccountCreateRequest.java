package com.boriworld.boriPaw.userAccountService.command.interfaces.request;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {
    private String email;
    private String accountName;
    private String password;
    private String nickname;

    public UserAccountCreate toAccountCreate() {
        return new UserAccountCreate(email, accountName, password, nickname);
    }
}
