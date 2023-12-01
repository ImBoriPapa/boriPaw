package com.boriworld.boriPaw.userAccountService.command.domain.model;

public final class RefreshTokenId {
    private final String id;
    private RefreshTokenId(String id) {
        this.id = id;
    }

    public static RefreshTokenId of(String id) {
        return new RefreshTokenId(id);
    }
}
