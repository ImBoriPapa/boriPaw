package com.boriworld.boriPaw.userAccountService.command.infrastructure.imports;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AccessTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * Domain Service
 */
@Component
@Slf4j
public final class JwtAuthenticationTokenServiceImpl implements AuthenticationTokenService {
    private final String SECRET_KEY;
    private final int ACCESS_TOKEN_LIFE_MINUTES;
    private final int REFRESH_TOKEN_LIFE_DAYS;

    public JwtAuthenticationTokenServiceImpl(
            @Value("secrete.secrete_key") String secreteKey,
            @Value("secrete.access_token_life_minutes") int accessTokenLifeMinutes,
            @Value("secrete.refresh_token_life_days") int refreshTokenLifeDays) {
        this.SECRET_KEY = secreteKey;
        this.ACCESS_TOKEN_LIFE_MINUTES = accessTokenLifeMinutes;
        this.REFRESH_TOKEN_LIFE_DAYS = refreshTokenLifeDays;
    }

    public String generateToken(AuthenticationTokenCredentials authenticationTokenCredentials, AuthenticationTokenType tokenType) {
        final Date nowDate = getNowDate();
        final Date expiryDate = new Date(nowDate.getTime() + getTokenExpiry(tokenType));
        return Jwts.builder()
                .setSubject(authenticationTokenCredentials.userAccountId().getId().toString())
                .setClaims(authenticationTokenCredentials.claims())
                .setIssuedAt(nowDate)
                .setExpiration(expiryDate)
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }


    public AccessTokenStatus validateToken(String token) {
        try {
            getClaimsJws(token);
            log.info("access token verification result= {}", AccessTokenStatus.ACCESS);
            return AccessTokenStatus.ACCESS;
        } catch (ExpiredJwtException e) {
            log.info("access token verification result= {}", AccessTokenStatus.EXPIRED);
            return AccessTokenStatus.EXPIRED;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("access token verification result= {}", AccessTokenStatus.DENIED);
            return AccessTokenStatus.DENIED;
        } catch (Exception e) {
            return AccessTokenStatus.ERROR;
        }
    }

    public String getSubject(String token) {
        return getBody(token)
                .getSubject();
    }

    public Object getClaim(String token, String value) {
        return getBody(token)
                .get(value);
    }

    @Override
    public Date getExpiredDate(String generateToken) {
        return getClaimsJws(generateToken)
                .getBody()
                .getExpiration();
    }

    private Claims getBody(String token) {
        return getClaimsJws(token)
                .getBody();
    }

    private Jws<Claims> getClaimsJws(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token);
    }
    private Date getNowDate() {
        return new Date(System.currentTimeMillis());
    }

    private long getTokenExpiry(AuthenticationTokenType tokenType) {
        return tokenType.getExpirationTimeUnit().toMillis((tokenType == AuthenticationTokenType.ACCESS_TOKEN) ? ACCESS_TOKEN_LIFE_MINUTES : REFRESH_TOKEN_LIFE_DAYS);
    }


    private Key getKey() {
        return Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()).getBytes());
    }
}
