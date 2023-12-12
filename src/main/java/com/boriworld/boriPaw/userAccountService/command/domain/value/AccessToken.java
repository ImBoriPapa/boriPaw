package com.boriworld.boriPaw.userAccountService.command.domain.value;


import com.boriworld.boriPaw.userAccountService.command.domain.dto.AccessTokenCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.service.SecurityContextManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


import java.util.Map;
import java.util.Objects;

/**
 * 역할 AccessToken 의 생성과 재발급, 인증처리
 * 책임 AccessToken 의 생성과 재발급, 인증처리의 비즈니스 로직을 관리
 */
@Slf4j
@Getter
public final class AccessToken {
    private final String tokenString;

    private AccessToken(final String tokenString) {
        Objects.requireNonNull(tokenString);
        this.tokenString = tokenString;
    }

    /**
     * 문자열 토큰으로 AccessToken 객체 생성
     */
    public static AccessToken createFromTokenString(final String token) {
        return new AccessToken(token);
    }

    /**
     * AccessTokenCreate, AuthenticationTokenService AuthenticationTokenPayloadEncoder 으로  AccessToken 생성
     *
     * @param accessTokenCreate : AccessToken 생성시 필요한 정보 전달 객체
     * @param tokenService      문자열 토큰의 생성, 데이터 추출, 검증 책임 객체
     * @param encoder           토큰의 클래임의 암호화 및 디코드 객체
     * @return AccessToken :
     * Subject- UserAccountId,
     * clamis- (encoded)authority
     * 정보가 포함된 문자열 토큰 tokenString 정보를 가지고 있습니다.
     */
    public static AccessToken from(AccessTokenCreate accessTokenCreate, AuthenticationTokenService tokenService, AuthenticationTokenPayloadEncoder encoder) {
        log.info("create access token");
        AuthenticationTokenCredentials credentials = createAuthenticationTokenCredentials(accessTokenCreate, encoder);
        return new AccessToken(generateTokenString(credentials, tokenService));
    }

    private static AuthenticationTokenCredentials createAuthenticationTokenCredentials(AccessTokenCreate accessTokenCreate, AuthenticationTokenPayloadEncoder encoder) {
        final String subject = accessTokenCreate.userAccountId().getId().toString();
        return new AuthenticationTokenCredentials(subject, encodeClaims(accessTokenCreate, encoder));
    }

    private static String generateTokenString(AuthenticationTokenCredentials credentials, AuthenticationTokenService tokenService) {
        return tokenService.generateTokenString(credentials, AuthenticationTokenType.ACCESS_TOKEN);
    }

    private static Map<String, String> encodeClaims(AccessTokenCreate accessTokenCreate, AuthenticationTokenPayloadEncoder encoder) {
        return Map.of("authority", encoder.encode(accessTokenCreate.authority().name()));
    }

    public static AccessToken reissueFromRefresh(RefreshToken reissuedRefreshToken) {
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
    public void processingAuthentication(AuthenticationTokenService service, AuthenticationTokenPayloadEncoder encoder, SecurityContextManager manager) {
        log.info("accessToken do authentication processing");
        validateTokenStatus(service);

        manager.setAuthentication(getUserAccountId(service), getDecodedAuthority(service, encoder));
    }

    private void validateTokenStatus(AuthenticationTokenService service) {
        log.info("access token validate");
        AuthenticationTokenStatus tokenStatus = service.validateToken(this.tokenString);

        if (tokenStatus != AuthenticationTokenStatus.ACCESS) {
            tokenStatus.throwAccessTokenErrorException();
        }
    }

    public UserAccountId getUserAccountId(AuthenticationTokenService service) {
        return UserAccountId.of(Long.parseLong(service.getSubject(this.tokenString)));
    }

    private Authority getDecodedAuthority(AuthenticationTokenService service, AuthenticationTokenPayloadEncoder encoder) {
        return Authority.formString(encoder.decode(String.valueOf(service.getClaim(this.tokenString, "authority"))));
    }
}
