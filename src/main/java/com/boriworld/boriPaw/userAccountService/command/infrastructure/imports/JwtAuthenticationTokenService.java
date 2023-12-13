package com.boriworld.boriPaw.userAccountService.command.infrastructure.imports;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

/**
 * Jwt 를 이용한 인증 토큰 생성,검증등
 */
@Component
@Slf4j
public final class JwtAuthenticationTokenService implements AuthenticationTokenService {
    private final String SECRET_KEY;
    private final long ACCESS_TOKEN_LIFE_MILLI_SECONDS;
    private final long REFRESH_TOKEN_LIFE_MILLI_SECONDS;

    public JwtAuthenticationTokenService(
            @Value("${secrete.secrete_key}") String secreteKey,
            @Value("${secrete.accessTokenLifeMillieSeconds}") long accessTokenLifeMillieSeconds,
            @Value("${secrete.refreshTokenLifeMillieSeconds}") long refreshTokenLifeMillieSeconds) {
        this.SECRET_KEY = secreteKey;
        this.ACCESS_TOKEN_LIFE_MILLI_SECONDS = accessTokenLifeMillieSeconds;
        this.REFRESH_TOKEN_LIFE_MILLI_SECONDS = refreshTokenLifeMillieSeconds;
    }

    public String generateTokenString(AuthenticationTokenCredentials credentials, AuthenticationTokenType type) {
        log.debug("generate token string");
        Date nowDate = Date.from(Instant.now());
        long expiration = type == AuthenticationTokenType.ACCESS_TOKEN ? ACCESS_TOKEN_LIFE_MILLI_SECONDS : REFRESH_TOKEN_LIFE_MILLI_SECONDS;
        Date expiryDate = new Date(nowDate.getTime() + expiration);
        return Jwts.builder()
                /*
                    @Issue setClaims() 이전에 setSubject() 를 사용하면 getSubject() = null 이슈
                    setClaims()를 먼저 사용하고 setSubject()를 사용해야 합니다.
                 */
                .setClaims(credentials.claims())
                .setSubject(credentials.subject())
                .setIssuedAt(nowDate)
                .setExpiration(expiryDate)
                .signWith(getEncodedSecreteKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public AuthenticationTokenStatus validateToken(String token) {
        try {
            getClaimsJws(token);
            log.info("access token verification result= {}", AuthenticationTokenStatus.ACCESS);
            return AuthenticationTokenStatus.ACCESS;
        } catch (ExpiredJwtException e) {
            log.info("access token verification result= {}", AuthenticationTokenStatus.EXPIRED);
            return AuthenticationTokenStatus.EXPIRED;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("access token verification result= {}", AuthenticationTokenStatus.DENIED);
            return AuthenticationTokenStatus.DENIED;
        } catch (Exception e) {
            return AuthenticationTokenStatus.ERROR;
        }
    }

    public String getSubject(String token) {
        return getBody(token)
                .getSubject();
    }

    public Object getClaim(String tokenString, String key) {
        return getBody(tokenString)
                .get(key);
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
                .setSigningKey(this.getEncodedSecreteKey())
                .build()
                .parseClaimsJws(token);
    }

    private Key getEncodedSecreteKey() {
        return Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()).getBytes());
    }
}
