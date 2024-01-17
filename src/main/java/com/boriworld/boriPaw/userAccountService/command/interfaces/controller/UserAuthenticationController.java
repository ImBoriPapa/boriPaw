package com.boriworld.boriPaw.userAccountService.command.interfaces.controller;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.common.constant.AuthenticationTokenHeaderNames;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidator;
import com.boriworld.boriPaw.userAccountService.command.application.UserAuthenticationService;
import com.boriworld.boriPaw.userAccountService.command.application.dto.LoginProcess;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationToken;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.TokenReissueFailException;
import com.boriworld.boriPaw.userAccountService.command.domain.model.AccessToken;
import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.LoginRequest;
import com.boriworld.boriPaw.userAccountService.command.interfaces.response.LoginResponse;
import com.boriworld.boriPaw.userAccountService.command.interfaces.response.ReissueResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationController {
    private final UserAuthenticationService userAuthenticationService;
    private final RequestConstraintValidator validator;

    @PostMapping(ApiEndpoints.LOGIN_PATH)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("login request");
        validator.validate(request);
        AuthenticationToken authenticationToken = userAuthenticationService.processLogin(new LoginProcess(request.email, request.password));
        AccessToken accessToken = authenticationToken.accessToken();
        RefreshToken refreshToken = authenticationToken.refreshToken();

        return ResponseEntity
                .ok()
                .headers(createHeaders(accessToken, refreshToken))
                .body(new LoginResponse(accessToken.getUserAccountId().getId()));
    }

    @PostMapping(ApiEndpoints.RE_ISSUE_PATH)
    public ResponseEntity<ReissueResponse> reIssue(@CookieValue(name = AuthenticationTokenHeaderNames.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshCookie) {
        validateCookie(refreshCookie);
        AuthenticationToken authenticationToken = userAuthenticationService.processReissueToken(refreshCookie);
        AccessToken accessToken = authenticationToken.accessToken();
        RefreshToken refreshToken = authenticationToken.refreshToken();

        return ResponseEntity
                .ok()
                .headers(createHeaders(accessToken, refreshToken))
                .body(new ReissueResponse(accessToken.getUserAccountId().getId()));
    }

    private void validateCookie(String refreshCookie) {
        try {
            Objects.requireNonNull(refreshCookie, "Refresh Token Cookie 를 찾을 수 없습니다.");
        } catch (NullPointerException e) {
            throw new TokenReissueFailException(e.getMessage());
        }
    }

    @PostMapping(ApiEndpoints.LOGOUT_PATH)
    public ResponseEntity<Void> logout(@PathVariable Long userAccountId) {

        userAuthenticationService.processLogout(userAccountId);

        return ResponseEntity.ok().body(null);
    }

    private HttpHeaders createHeaders(AccessToken accessToken, RefreshToken refreshToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER, AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX + accessToken.getTokenString());
        httpHeaders.set(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(refreshToken));
        return httpHeaders;
    }

    private String createRefreshTokenCookie(RefreshToken refreshToken) {
        ResponseCookie refresh = ResponseCookie.from(AuthenticationTokenHeaderNames.REFRESH_TOKEN_COOKIE_NAME, refreshToken.getTokenString())
                .secure(true)
                .sameSite("None")
                .httpOnly(false)
                .path("/")
                .maxAge(Duration.ofMillis(refreshToken.getTimeToLiveInMilliseconds()))
                .build();
        return refresh.toString();
    }

}
