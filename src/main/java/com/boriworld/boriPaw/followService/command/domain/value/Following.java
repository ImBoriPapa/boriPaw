package com.boriworld.boriPaw.followService.command.domain.value;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "userAccountId")
public final class Following {
    private final UserAccountId userAccountId;

    private Following(UserAccountId userAccountId) {
        this.userAccountId = userAccountId;
    }

    public static Following of(Long id) {
        return new Following(UserAccountId.of(id));
    }

}
