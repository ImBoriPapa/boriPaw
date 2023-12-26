package com.boriworld.boriPaw.testData;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
@ActiveProfiles("test")
public class TestJwtTokenFactory {
    private final String SECRET_KEY;

    public TestJwtTokenFactory(
            @Value("${secrete.secrete_key}") String secreteKey) {
        this.SECRET_KEY = secreteKey;
    }

    public String generateTokenString(AuthenticationTokenCredentials credentials, Date start,Date expiration) {

        return Jwts.builder()
                /*
                    @Issue setClaims() 이전에 setSubject() 를 사용하면 getSubject() = null 이슈
                    setClaims()를 먼저 사용하고 setSubject()를 사용해야 합니다.
                 */
                .setClaims(credentials.claims())
                .setSubject(credentials.subject())
                .setIssuedAt(start)
                .setExpiration(expiration)
                .signWith(getEncodedSecreteKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public AuthenticationTokenStatus validateToken(String token) {
        try {
            getClaimsJws(token);
            return AuthenticationTokenStatus.ACCESS;
        } catch (ExpiredJwtException e) {
            return AuthenticationTokenStatus.EXPIRED;
        } catch (JwtException | IllegalArgumentException e) {
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
