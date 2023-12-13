package com.boriworld.boriPaw.userAccountService.command.domain.model;


import com.boriworld.boriPaw.userAccountService.command.domain.useCase.AccessTokenCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.service.SecurityContextManager;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.AccessTokenReissue;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenType;
import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


import java.util.Map;
import java.util.Objects;

/**
 * Domain Model
 * -  AccessToken 생성, 재발급, 인증등 비즈니스 로직을 처리하는 도메인 모델입니다.
 * -  데이터베이스에 저장하지 않는 데이터이지만 tokenString 을 식별 아이디로 간주하고 도메인 모델로 설계되었습니다.
 * 역할 AccessToken 의 생성과 재발급, 인증처리
 * 책임 AccessToken 의 생성과 재발급, 인증처리의 비즈니스 로직을 관리
 */
@Slf4j
@Getter
public final class AccessToken {
    private final String tokenString;
    private final UserAccountId userAccountId;
    private final Authority authority;

    private AccessToken(final String tokenString, final UserAccountId userAccountId, final Authority authority) {
        checkNullParameter(tokenString, userAccountId, authority);
        this.tokenString = tokenString;
        this.userAccountId = userAccountId;
        this.authority = authority;
    }

    private void checkNullParameter(String tokenString, UserAccountId userAccountId, Authority authority) {
        Objects.requireNonNull(tokenString, "tokenString must not be null");
        Objects.requireNonNull(userAccountId, "userAccountId must not be null");
        Objects.requireNonNull(authority, "userAccountId must not be null");
    }

    /**
     * AccessTokenCreate, AuthenticationTokenService AuthenticationTokenPayloadEncoder 으로  AccessToken 생성
     *
     * @param accessTokenCreate : AccessToken 생성시 필요한 정보 전달 객체
     * @param tokenService      문자열 토큰의 생성, 데이터 추출, 검증 책임 객체
     * @param encoder           토큰의 클래임의 암호화 및 디코드 객체
     * @return AccessToken :
     * Subject- UserAccountId,
     * claims- (encoded)authority
     * 정보가 포함된 문자열 토큰 tokenString 정보를 가지고 있습니다.
     */
    public static AccessToken createFrom(AccessTokenCreate accessTokenCreate, AuthenticationTokenService tokenService, AuthenticationTokenPayloadEncoder encoder) {
        checkNullObject(accessTokenCreate, tokenService, encoder);
        return new AccessToken(
                tokenService.generateTokenString(createAuthenticationTokenCredentials(accessTokenCreate, encoder), AuthenticationTokenType.ACCESS_TOKEN),
                accessTokenCreate.userAccountId(),
                accessTokenCreate.authority());
    }

    private static AuthenticationTokenCredentials createAuthenticationTokenCredentials(AccessTokenCreate accessTokenCreate, AuthenticationTokenPayloadEncoder encoder) {
        return new AuthenticationTokenCredentials(
                accessTokenCreate.userAccountId().getId().toString(),
                encodeClaims(accessTokenCreate, encoder));
    }

    private static Map<String, String> encodeClaims(AccessTokenCreate accessTokenCreate, AuthenticationTokenPayloadEncoder encoder) {
        return Map.of("authority", encoder.encode(accessTokenCreate.authority().name()));
    }

    private static void checkNullObject(AccessTokenCreate accessTokenCreate, AuthenticationTokenService tokenService, AuthenticationTokenPayloadEncoder encoder) {
        Objects.requireNonNull(accessTokenCreate, "AccessTokenCreate must not be null");
        Objects.requireNonNull(tokenService, "AuthenticationTokenService must not be null");
        Objects.requireNonNull(encoder, "SecurityContextManager must not be null");
    }


    public static AccessToken reissueFromRefresh(AccessTokenReissue accessTokenReissue) {
        return null;
    }

    /**
     * 토큰을 검증 후 인증 성공 혹은 실패 처리
     * 검증 성공시 SecurityContextManager 는 Authentication 객체를 저장
     * 검증 실패시 AuthenticationTokenException
     *
     * @param service 토큰의 검증 책임
     * @param encoder 토큰의 클래임의 비암호화 책임
     * @param manager 인증정보 관리
     */
    public static AccessToken processingAuthenticationFromTokenString(String tokenString, AuthenticationTokenService service, AuthenticationTokenPayloadEncoder encoder, SecurityContextManager manager) {
        log.info("accessToken do authentication processing");

        AuthenticationTokenStatus tokenStatus = service.validateToken(tokenString);
        if (tokenStatus != AuthenticationTokenStatus.ACCESS) {
            tokenStatus.throwAccessTokenErrorException();
        }

        UserAccountId accountId = UserAccountId.of(Long.parseLong(service.getSubject(tokenString)));
        Authority authority = Authority.formString(encoder.decode(service.getClaim(tokenString, "authority").toString()));
        manager.setAuthentication(accountId, authority);
        return new AccessToken(tokenString, accountId, authority);
    }
}
