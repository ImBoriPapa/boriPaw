package com.boriworld.boriPaw.userAccountService.command.domain.event;


import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import lombok.Getter;

@Getter
public final class AccountCreateEvent implements AccountEvent {
    private final UserAccountId userAccountId;
    private final String email;

    private AccountCreateEvent(UserAccountId userAccountId, String email) {
        this.userAccountId = userAccountId;
        this.email = email;
    }

    public static AccountCreateEvent of(UserAccount userAccount) {

        return new AccountCreateEvent(userAccount.getUserAccountId(), userAccount.getEmail());
    }
}
