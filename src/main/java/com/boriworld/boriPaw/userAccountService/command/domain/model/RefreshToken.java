package com.boriworld.boriPaw.userAccountService.command.domain.model;

public final class RefreshToken {
    private final RefreshTokenId refreshTokenId;
    private final String token;
    private final long expired;

    private RefreshToken(RefreshTokenId refreshTokenId, String token, long expired) {
        this.refreshTokenId = refreshTokenId;
        this.token = token;
        this.expired = expired;
    }

    public static RefreshToken of(String token, long expired) {
        return new RefreshToken(null, token, expired);
    }
}
