package com.boriworld.boriPaw.followService.query.domain.value;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import lombok.Getter;

@Getter
public final class FollowerThatIFollow {
    private final UserAccountId userAccountId;

    private FollowerThatIFollow(UserAccountId userAccountId) {
        this.userAccountId = userAccountId;
    }

    public static FollowerThatIFollow of(Long id) {
        return new FollowerThatIFollow(UserAccountId.of(id));
    }
}
