package com.boriworld.boriPaw.userAccountService.command.interfaces.controller;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.common.constant.AuthenticationTokenHeaders;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidator;
import com.boriworld.boriPaw.userAccountService.command.application.UserAccountAuthenticationService;
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
public class UserAccountAuthenticationController {
    private final UserAccountAuthenticationService userAccountAuthenticationService;
    private final RequestConstraintValidator validator;

    @PostMapping(ApiEndpoints.LOGIN_PATH)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("login request");
        validator.validate(request);
        AuthenticationToken authenticationToken = userAccountAuthenticationService.processLogin(new LoginProcess(request.email, request.password));
        AccessToken accessToken = authenticationToken.accessToken();
        RefreshToken refreshToken = authenticationToken.refreshToken();

        return ResponseEntity
                .ok()
                .headers(createHeaders(accessToken, refreshToken))
                .body(new LoginResponse(accessToken.getUserAccountId().getId()));
    }

    @PostMapping(ApiEndpoints.RE_ISSUE_PATH)
    public ResponseEntity<ReissueResponse> reIssue(@CookieValue(name = AuthenticationTokenHeaders.REFRESH_TOKEN_COOKIE_HEADER, required = false) String refreshCookie) {

        validateCookie(refreshCookie);
        AuthenticationToken authenticationToken = userAccountAuthenticationService.processReissueToken(refreshCookie);
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

        userAccountAuthenticationService.processLogout(userAccountId);

        return ResponseEntity.ok().body(null);
    }

    private HttpHeaders createHeaders(AccessToken accessToken, RefreshToken refreshToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AuthenticationTokenHeaders.AUTHORIZATION_HEADER, AuthenticationTokenHeaders.ACCESS_TOKEN_PREFIX + accessToken.getTokenString());
        httpHeaders.set(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(refreshToken));
        return httpHeaders;
    }

    private String createRefreshTokenCookie(RefreshToken refreshToken) {
        ResponseCookie refresh = ResponseCookie.from(AuthenticationTokenHeaders.REFRESH_TOKEN_COOKIE_HEADER, refreshToken.getTokenString())
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofMillis(refreshToken.getTimeToLiveInMilliseconds()))
                .build();
        return refresh.toString();
    }

}
