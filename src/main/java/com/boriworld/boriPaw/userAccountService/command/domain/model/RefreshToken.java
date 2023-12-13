package com.boriworld.boriPaw.userAccountService.command.domain.model;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.RefreshTokenCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.value.*;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

/**
 * Domain Model
 */
@Getter
public final class RefreshToken {
    private final RefreshTokenId refreshTokenId;
    private final String tokenString;
    private final long timeToLiveInMilliseconds;

    private RefreshToken(RefreshTokenId refreshTokenId, String tokenString, long timeToLiveInMilliseconds) {
        this.refreshTokenId = refreshTokenId;
        this.tokenString = tokenString;
        this.timeToLiveInMilliseconds = timeToLiveInMilliseconds;
    }


    public static RefreshToken createFrom(RefreshTokenCreate refreshTokenCreate, AuthenticationTokenService service, AuthenticationTokenPayloadEncoder encoder) {
        Objects.requireNonNull(refreshTokenCreate, "RefreshTokenCreate must be not null");
        Objects.requireNonNull(service, "AuthenticationTokenService must be not null");
        Objects.requireNonNull(encoder, "AuthenticationTokenPayloadEncoder must be not null");
        UserAccountId userAccountId = refreshTokenCreate.userAccountId();
        String encodedAuthority = encoder.encode(refreshTokenCreate.authority().name());
        AuthenticationTokenCredentials credentials = createTokenCredentials(userAccountId, encodedAuthority);
        String generateToken = service.generateTokenString(credentials, AuthenticationTokenType.REFRESH_TOKEN);
        Instant expirationTime = service.getExpiredDate(generateToken).toInstant();
        return new RefreshToken(RefreshTokenId.of(userAccountId), generateToken, calculateTimeToLive(expirationTime));
    }

    public static RefreshToken createFromTokenString(final String tokenString, AuthenticationTokenService service) {
        UserAccountId userAccountId = UserAccountId.of(Long.parseLong(service.getSubject(tokenString)));
        long calculateTimeToLive = calculateTimeToLive(service.getExpiredDate(tokenString).toInstant());
        return new RefreshToken(RefreshTokenId.of(userAccountId), tokenString, calculateTimeToLive);
    }

    private static AuthenticationTokenCredentials createTokenCredentials(UserAccountId userAccountId, String encodedAuthority) {
        return new AuthenticationTokenCredentials(userAccountId.getId().toString(), Map.of("authority", encodedAuthority));
    }

    public RefreshToken reissue(AuthenticationTokenService tokenService, AuthenticationTokenPayloadEncoder encoder) {
        AuthenticationTokenStatus authenticationTokenStatus = tokenService.validateToken(this.tokenString);

        if (authenticationTokenStatus != AuthenticationTokenStatus.ACCESS) {
            authenticationTokenStatus.throwAccessTokenErrorException();
        }

        UserAccountId userAccountId = UserAccountId.of(Long.parseLong(tokenService.getSubject(this.tokenString)));
        String encodedAuthority = tokenService.getClaim(this.tokenString, "authority").toString();
        AuthenticationTokenCredentials tokenCredentials = createTokenCredentials(userAccountId, encodedAuthority);

        String generateToken = tokenService.generateTokenString(tokenCredentials, AuthenticationTokenType.REFRESH_TOKEN);
        Instant expirationTime = tokenService.getExpiredDate(generateToken).toInstant();

        return new RefreshToken(RefreshTokenId.of(userAccountId), generateToken, calculateTimeToLive(expirationTime));
    }


    private static long calculateTimeToLive(Instant expirationTime) {
        return Duration.between(Instant.now(), expirationTime).toMillis();
    }


}
