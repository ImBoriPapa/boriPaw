package com.boriworld.boriPaw.userAccountService.command.domain.value;

import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
import lombok.Getter;

@Getter
public final class RefreshTokenId {
    private final UserAccountId userAccountId;
    private RefreshTokenId(UserAccountId userAccountId) {
        this.userAccountId = userAccountId;
    }

    public static RefreshTokenId of(UserAccountId userAccountId) {
        return new RefreshTokenId(userAccountId);
    }


}
