package com.boriworld.boriPaw.followService.query.domain.value;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import lombok.Getter;

@Getter
public final class Requester {
    private final UserAccountId userAccountId;

    private Requester(UserAccountId userAccountId) {
        this.userAccountId = userAccountId;
    }

    public static Requester of(UserAccountId userAccountId) {
        return new Requester(userAccountId);
    }
}
