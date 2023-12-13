package com.boriworld.boriPaw.fakeTestComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

public class FakeAuthenticationTokenService implements AuthenticationTokenService {
    private final String FAKE_SECRETE_KEY = "fakefakefakefakefakefakefakefakefakefakefakefakefake";
    private final long ACCESS_TOKEN_LIFE_MILLI_SECOND;
    private final long REFRESH_TOKEN_LIFE_MILLI_SECOND;
    private final Key key = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(FAKE_SECRETE_KEY.getBytes()).getBytes());

    public FakeAuthenticationTokenService(long accessTokenLifeMilliSecond, long refreshTokenLifeMillieSecond) {
        this.ACCESS_TOKEN_LIFE_MILLI_SECOND = accessTokenLifeMilliSecond;
        this.REFRESH_TOKEN_LIFE_MILLI_SECOND = refreshTokenLifeMillieSecond;
    }

    @Override
    public String generateTokenString(AuthenticationTokenCredentials credentials, AuthenticationTokenType type) {
        final Date nowDate = new Date(System.currentTimeMillis());
        long expiration = type == AuthenticationTokenType.ACCESS_TOKEN ? ACCESS_TOKEN_LIFE_MILLI_SECOND : REFRESH_TOKEN_LIFE_MILLI_SECOND;
        return Jwts.builder()
                .setClaims(credentials.claims())
                .setSubject(credentials.subject())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date(nowDate.getTime() + expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public AuthenticationTokenStatus validateToken(String tokenString) {
        try {
            getParesClaims(tokenString);
            return AuthenticationTokenStatus.ACCESS;
        } catch (ExpiredJwtException e) {
            return AuthenticationTokenStatus.EXPIRED;
        } catch (JwtException | IllegalArgumentException e) {
            return AuthenticationTokenStatus.DENIED;
        } catch (Exception e) {
            return AuthenticationTokenStatus.ERROR;
        }
    }

    private Jws<Claims> getParesClaims(String tokenString) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(tokenString);
    }

    @Override
    public String getSubject(String tokenString) {
        return getParesClaims(tokenString)
                .getBody()
                .getSubject();
    }

    @Override
    public Object getClaim(String tokenString, String key) {
        return getParesClaims(tokenString)
                .getBody()
                .get(key);
    }

    @Override
    public Date getExpiredDate(String tokenString) {
        return getParesClaims(tokenString)
                .getBody()
                .getExpiration();
    }
}
