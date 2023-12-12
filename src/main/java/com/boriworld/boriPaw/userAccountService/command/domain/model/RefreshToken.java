package com.boriworld.boriPaw.userAccountService.command.domain.model;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.value.*;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * Domain Model
 */
@Getter
public final class RefreshToken {
    private final RefreshTokenId refreshTokenId;
    private final String token;
    private final long timeToLive;

    private RefreshToken(RefreshTokenId refreshTokenId, String token, long timeToLive) {
        this.refreshTokenId = refreshTokenId;
        this.token = token;
        this.timeToLive = timeToLive;
    }

    public static RefreshToken create(UserAccount userAccount, AuthenticationTokenService service, AuthenticationTokenPayloadEncoder encoder) {
        UserAccountId userAccountId = userAccount.getUserAccountId();
        String encodedAuthority = encoder.encode(userAccount.getAuthority().name());
        AuthenticationTokenCredentials credentials = createTokenCredentials(userAccountId, encodedAuthority);
        String generateToken = service.generateTokenString(credentials, AuthenticationTokenType.REFRESH_TOKEN);
        Instant expirationTime = service.getExpiredDate(generateToken).toInstant();
        return new RefreshToken(RefreshTokenId.of(userAccountId), generateToken, calculateTimeToLive(expirationTime));
    }

    private static AuthenticationTokenCredentials createTokenCredentials(UserAccountId userAccountId, String encodedAuthority) {
        return new AuthenticationTokenCredentials(userAccountId.getId().toString(), Map.of("authority", encodedAuthority));
    }

    public RefreshToken reissue(AuthenticationTokenService tokenService, AuthenticationTokenPayloadEncoder encoder) {
        AuthenticationTokenStatus authenticationTokenStatus = tokenService.validateToken(this.token);

        if (authenticationTokenStatus != AuthenticationTokenStatus.ACCESS) {
            authenticationTokenStatus.throwAccessTokenErrorException();
        }

        UserAccountId userAccountId = UserAccountId.of(Long.parseLong(tokenService.getSubject(this.token)));
        String encodedAuthority = tokenService.getClaim(this.token, "authority").toString();
        AuthenticationTokenCredentials tokenCredentials = createTokenCredentials(userAccountId, encodedAuthority);

        String generateToken = tokenService.generateTokenString(tokenCredentials, AuthenticationTokenType.REFRESH_TOKEN);
        Instant expirationTime = tokenService.getExpiredDate(generateToken).toInstant();

        return new RefreshToken(RefreshTokenId.of(userAccountId), generateToken, calculateTimeToLive(expirationTime));
    }

    public static RefreshToken fromTokenString(final String tokenString, AuthenticationTokenService service) {
        UserAccountId userAccountId = UserAccountId.of(Long.parseLong(service.getSubject(tokenString)));
        long calculateTimeToLive = calculateTimeToLive(service.getExpiredDate(tokenString).toInstant());
        return new RefreshToken(RefreshTokenId.of(userAccountId), tokenString, calculateTimeToLive);
    }


    private static long calculateTimeToLive(Instant expirationTime) {
        return Duration.between(Instant.now(), expirationTime).toMillis();
    }


}
