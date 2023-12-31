package com.boriworld.boriPaw.followService.command.domain.value;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "userAccountId")
public final class Follower {
    private final UserAccountId userAccountId;

    private Follower(UserAccountId userAccountId) {
        this.userAccountId = userAccountId;
    }

    public static Follower of(Long id) {
        return new Follower(UserAccountId.of(id));
    }
}
