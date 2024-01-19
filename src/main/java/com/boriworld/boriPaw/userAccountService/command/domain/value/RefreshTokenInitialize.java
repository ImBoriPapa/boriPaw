package com.boriworld.boriPaw.userAccountService.command.domain.value;

public record RefreshTokenInitialize(String refreshTokenId, String tokenString, long timeToLive) {
}
