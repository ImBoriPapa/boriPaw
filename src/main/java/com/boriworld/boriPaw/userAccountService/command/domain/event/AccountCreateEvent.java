package com.boriworld.boriPaw.userAccountService.command.domain.event;


import com.boriworld.boriPaw.userAccountService.command.domain.value.AccountId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateEvent implements AccountEvent {
    private AccountId accountId;
    private String email;

}
