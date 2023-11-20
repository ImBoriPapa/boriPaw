package com.boriworld.boriPaw.accountService.command.interfaces.request;

import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
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

    public AccountCreate toAccountCreate() {
        return new AccountCreate(email, accountName, password, nickname);
    }
}
