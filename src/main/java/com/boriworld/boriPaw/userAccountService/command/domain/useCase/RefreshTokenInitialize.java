package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

public record RefreshTokenInitialize(String refreshTokenId, String tokenString, long timeToLive) {
}
