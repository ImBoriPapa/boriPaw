package com.boriworld.boriPaw.fakeTestComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class FakeAuthenticationTokenService implements AuthenticationTokenService {
    private final String FAKE_SECRETE_KEY = "fakefakefakefakefakefakefakefakefakefakefakefakefake";
    private final int ACCESS_TOKEN_LIFE;
    private final int REFRESH_TOKEN_LIFE;
    private final Key key = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(FAKE_SECRETE_KEY.getBytes()).getBytes());

    public FakeAuthenticationTokenService(int ACCESS_TOKEN_LIFE, int REFRESH_TOKEN_LIFE) {
        this.ACCESS_TOKEN_LIFE = ACCESS_TOKEN_LIFE;
        this.REFRESH_TOKEN_LIFE = REFRESH_TOKEN_LIFE;
    }

    @Override
    public String generateTokenString(AuthenticationTokenCredentials authenticationTokenCredentials, AuthenticationTokenType tokenType) {
        final Date nowDate = new Date(System.currentTimeMillis());

        long life = tokenType == AuthenticationTokenType.ACCESS_TOKEN ? ACCESS_TOKEN_LIFE : REFRESH_TOKEN_LIFE;

        final Date expiryDate = new Date(nowDate.getTime() + TimeUnit.SECONDS.toMillis(life));
        return Jwts.builder()
                .setClaims(authenticationTokenCredentials.claims())
                .setSubject(authenticationTokenCredentials.subject())
                .setIssuedAt(nowDate)
                .setExpiration(expiryDate)
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
    public Object getClaim(String token, String key) {
        return getParesClaims(token)
                .getBody()
                .get(key);
    }

    @Override
    public Date getExpiredDate(String generateToken) {
        return null;
    }
}
