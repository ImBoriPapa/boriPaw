package com.boriworld.boriPaw.userAccountService.command.domain.value;


import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenService;
import com.boriworld.boriPaw.userAccountService.command.domain.service.SecurityContextManager;

import java.util.Map;

/**
 * Value Object
 */
public final class AccessToken {
    private final String token;

    private AccessToken(String token) {
        this.token = token;
    }

    /**
     * 문자열 토큰으로 AccessToken 객체 생성
     */
    public static AccessToken createFromTokenString(final String token) {
        return new AccessToken(token);
    }

    /**
     * AccessToken 생성
     *
     * @param userAccount  토큰에 저장할 정보 전달 책임
     * @param tokenService 토큰의 생성 책임
     * @param encoder      토큰의 클래임의 암호화 책임
     * @return AccessToken 암호화된 클래임 정보를 포함한 토큰
     */
    public static AccessToken create(UserAccount userAccount, AuthenticationTokenService tokenService, AuthenticationTokenPayloadEncoder encoder) {

        UserAccountId userAccountId = userAccount.getUserAccountId();
        Map<String, String> encodedClaims = getEncodedClaims(userAccount, encoder);

        AuthenticationTokenCredentials tokenCredentials = new AuthenticationTokenCredentials(userAccountId, encodedClaims);

        return new AccessToken(tokenService.generateToken(tokenCredentials, AuthenticationTokenType.ACCESS_TOKEN));
    }

    private static Map<String, String> getEncodedClaims(UserAccount userAccount, AuthenticationTokenPayloadEncoder encoder) {
        final String encodedAuthority = encoder.encode(userAccount.getAuthority().name());
        return Map.of("authority", encodedAuthority);
    }

    /**
     * 토큰을 검증 후 인증 성공 혹은 실패 처리
     *
     * @param service 토큰의 검증 책임
     * @param encoder 토큰의 클래임의 비암호화 책임
     * @param manager 인증된 정보 처리
     */
    public void processingAuthentication(AuthenticationTokenService service, AuthenticationTokenPayloadEncoder encoder, SecurityContextManager manager) {
        AccessTokenStatus tokenStatus = service.validateToken(this.token);

        handleAuthenticationFail(tokenStatus);

        manager.setAuthentication(getUserAccountId(service), getDecodedAuthority(service, encoder));
    }

    public UserAccountId getUserAccountId(AuthenticationTokenService service) {
        return UserAccountId.of(Long.parseLong(service.getSubject(this.token)));
    }

    private Authority getDecodedAuthority(AuthenticationTokenService service, AuthenticationTokenPayloadEncoder encoder) {
        return Authority.formString(encoder.decode(String.valueOf(service.getClaim(this.token, "authority"))));
    }

    private void handleAuthenticationFail(AccessTokenStatus tokenStatus) {
        if (tokenStatus != AccessTokenStatus.ACCESS) {
            tokenStatus.throwAccessTokenErrorException();
        }
    }


}
