package com.boriworld.boriPaw.userAccountService.command.domain.event;


import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateEvent implements AccountEvent {
    private UserAccountId userAccountId;
    private String email;

}
