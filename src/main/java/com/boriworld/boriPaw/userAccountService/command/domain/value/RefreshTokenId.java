package com.boriworld.boriPaw.userAccountService.command.domain.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public final class RefreshTokenId {
    private final String id;
    private RefreshTokenId(String id) {
        this.id = id;
    }

    public static RefreshTokenId from(UserAccountId userAccountId) {
        final String id = userAccountId.getId().toString();
        return new RefreshTokenId(id);
    }

    public static RefreshTokenId from(final String id) {
        return new RefreshTokenId(id);
    }

}
