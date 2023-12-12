package com.boriworld.boriPaw.userAccountService.command.infrastructure.imports;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenType;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

class JwtAuthenticationTokenServiceSmallTest {

    AuthenticationTokenService authenticationTokenService;

    @Test
    @DisplayName("(String)secreteKey,(long)accessTokenLifeMilliseconds,(long)refreshTokenLifeMilliseconds 으로 JwtAuthenticationTokenServiceImpl 생성")
    void test1() throws Exception {
        //given
        final String secreteKey = "secreteKey";
        final long acc = 6000;
        final long re = 604_800_000L;
        //when
        authenticationTokenService = new JwtAuthenticationTokenService(secreteKey, acc, re);
        //then
        assertThat(authenticationTokenService).isNotNull();

    }
    @Test
    @DisplayName("expired test")
    void test2() throws Exception {
        //given
        final String secreteKey = "sagjfadhfdgdsgdsakldsadgsgsdahjbgdsajgadsadsgdsafhad";
        final long acc = 1000;
        final long re = 604_800_000L;
        final String subject = "123";
        final Map<String, String> claims = Map.of("authority", "USER");
        AuthenticationTokenCredentials credentials = new AuthenticationTokenCredentials(subject, claims);
        //when
        authenticationTokenService = new JwtAuthenticationTokenService(secreteKey, acc, re);
        String tokenString = authenticationTokenService.generateTokenString(credentials, AuthenticationTokenType.ACCESS_TOKEN);
        Awaitility.await().atMost(2, TimeUnit.SECONDS)
                .until(()-> authenticationTokenService.validateToken(tokenString) == AuthenticationTokenStatus.EXPIRED);

        //then
        AuthenticationTokenStatus status = authenticationTokenService.validateToken(tokenString);
        assertThat(status).isEqualTo(AuthenticationTokenStatus.EXPIRED);
    }
}
