package com.boriworld.boriPaw.userAccountService.command.domain.model;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AccessTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenType;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RefreshTokenId;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.exception.UserAccountMismatchException;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * Domain Model
 */
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
        String generateToken = service.generateToken(createTokenCredentials(userAccount, getEncodedAuthority(userAccount, encoder)), AuthenticationTokenType.REFRESH_TOKEN);
        Instant expirationTime = service.getExpiredDate(generateToken).toInstant();
        return new RefreshToken(RefreshTokenId.of(userAccount.getUserAccountId()), generateToken, calculateTimeToLive(expirationTime));
    }

    private static AuthenticationTokenCredentials createTokenCredentials(UserAccount userAccount, String encodeAuthority) {
        return new AuthenticationTokenCredentials(userAccount.getUserAccountId(), Map.of("authority", encodeAuthority));
    }

    private static String getEncodedAuthority(UserAccount userAccount, AuthenticationTokenPayloadEncoder encoder) {
        return encoder.encode(userAccount.getAuthority().name());
    }

    private static long calculateTimeToLive(Instant expirationTime) {
        return Duration.between(Instant.now(), expirationTime).toMillis();
    }


    public RefreshToken reissue(String tokenString, UserAccount userAccount, AuthenticationTokenService authenticationTokenService, AuthenticationTokenPayloadEncoder authenticationTokenPayloadEncoder) {
        AccessTokenStatus accessTokenStatus = authenticationTokenService.validateToken(tokenString);

        if (accessTokenStatus != AccessTokenStatus.ACCESS) {
            accessTokenStatus.throwAccessTokenErrorException();
        }

        UserAccountId userAccountId = UserAccountId.of(Long.parseLong(authenticationTokenService.getSubject(tokenString)));
        if (!userAccount.getUserAccountId().equals(userAccountId)) {
            throw new UserAccountMismatchException("토큰이 사용자 계정과 일치하지 않습니다");
        }

        return create(userAccount, authenticationTokenService, authenticationTokenPayloadEncoder);
    }
}
