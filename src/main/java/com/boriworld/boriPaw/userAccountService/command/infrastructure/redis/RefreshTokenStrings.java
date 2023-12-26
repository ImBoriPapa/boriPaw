package com.boriworld.boriPaw.userAccountService.command.infrastructure.redis;

import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.RefreshTokenInitialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenStrings {
    private String refreshTokenId;
    private String tokenString;
    private long timeToLive;
    private TimeUnit timeUnit;
    protected RefreshTokenStrings(String refreshTokenId, String tokenString, long timeToLive, TimeUnit timeUnit) {
        this.refreshTokenId = refreshTokenId;
        this.tokenString = tokenString;
        this.timeToLive = timeToLive;
        this.timeUnit = timeUnit;
    }

    public static RefreshTokenStrings formModel(RefreshToken refreshToken) {
        return new RefreshTokenStrings(refreshToken.getRefreshTokenId().getId(), refreshToken.getTokenString(), refreshToken.getTimeToLiveInMilliseconds(), TimeUnit.MILLISECONDS);
    }

    public RefreshToken toModel() {
        return RefreshToken.initialize(new RefreshTokenInitialize(this.refreshTokenId, this.tokenString, this.timeToLive));
    }
}
