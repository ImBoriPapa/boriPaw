package com.boriworld.boriPaw.userAccountService.command.domain.value;


public final class RefreshTokenId {
    private final UserAccountId userAccountId;

    private RefreshTokenId(UserAccountId userAccountId) {
        this.userAccountId = userAccountId;
    }

    public static RefreshTokenId of(UserAccountId userAccountId) {
        return new RefreshTokenId(userAccountId);
    }
}
