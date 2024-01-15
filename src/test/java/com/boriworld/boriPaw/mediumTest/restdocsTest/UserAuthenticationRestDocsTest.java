package com.boriworld.boriPaw.mediumTest.restdocsTest;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.common.constant.AuthenticationTokenHeaderNames;
import com.boriworld.boriPaw.testContainer.testcontainer.RestDocsMediumTest;
import com.boriworld.boriPaw.testData.TestJwtTokenFactory;
import com.boriworld.boriPaw.testData.TestUserAccountsFactory;
import com.boriworld.boriPaw.userAccountService.command.application.UserAuthenticationService;
import com.boriworld.boriPaw.userAccountService.command.application.dto.LoginProcess;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationToken;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;
import com.boriworld.boriPaw.userAccountService.command.domain.model.AccessToken;
import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.LoginRequest;
import jakarta.servlet.http.Cookie;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;


import java.util.Date;
import java.util.Map;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAuthenticationRestDocsTest extends RestDocsMediumTest {
    @Autowired
    private TestUserAccountsFactory testUserAccountsFactory;
    @Autowired
    private UserAuthenticationService accountAuthenticationService;
    @Autowired
    private TestJwtTokenFactory factory;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Test
    @DisplayName("로그인 요청시 AccessToken 과 RefreshToken 응답")
    void givenLoginRequest_thenReturnStatusIsOkWithAccessTokenAndRefreshToken() throws Exception {
        //given
        testUserAccountsFactory.initTester();
        final String email = testUserAccountsFactory.TESTER_EMAIL;
        final String password = testUserAccountsFactory.TESTER_RAW_PASSWORD;
        LoginRequest request = new LoginRequest(email, password);
        //when
        ResultActions actions = mockMvc.perform(post(ApiEndpoints.LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)));
        //then
        actions.andExpectAll(
                status().is(HttpStatus.OK.value()),
                header().exists(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER),
                cookie().exists(AuthenticationTokenHeaderNames.REFRESH_TOKEN_COOKIE_NAME),
                jsonPath("$.userAccountId").exists()
        );
        //andDo
        actions.andDo(loginSuccessDocument());
    }

    @Test
    @DisplayName("잘못된 이메일로 로그인 요청시 404 응답")
    void givenLoginRequest_With_WrongEmail_thenReturn404Status() throws Exception {
        //given
        testUserAccountsFactory.initTester();
        final String notExistsEmail = "notExist@test.com";
        final String password = testUserAccountsFactory.TESTER_RAW_PASSWORD;
        LoginRequest request = new LoginRequest(notExistsEmail, password);
        //when
        ResultActions actions = mockMvc.perform(post(ApiEndpoints.LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)));
        //then
        actions.andExpectAll(
                status().is(HttpStatus.NOT_FOUND.value())
        );
        //andDo
        actions.andDo(loginFailDocument());
    }

    @Test
    @DisplayName("발급 받은 AccessToken 으로 인증 정보 확인이 가능하다.")
    void givenAccessToken_thenReturnAuthenticationInformation() throws Exception {
        //given
        testUserAccountsFactory.initTester();
        AuthenticationToken authenticationToken = userAuthenticationService.processLogin(new LoginProcess(testUserAccountsFactory.TESTER_EMAIL, testUserAccountsFactory.TESTER_RAW_PASSWORD));
        String accessToken = AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX + authenticationToken.accessToken().getTokenString();
        //when
        ResultActions actions = mockMvc.perform(get(ApiEndpoints.ME).header(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER, accessToken));
        //then
        actions.andExpectAll(
                status().isOk(),
                jsonPath("$.userAccountId").exists(),
                jsonPath("$.authority").exists(),
                jsonPath("$.lastLoginAt").exists()
        );

        //andDo
        actions.andDo(GetMeDocument());
    }


    @Test
    @DisplayName("만료된 AccessToken 으로 요청시 401 응답")
    void givenExpiredAccessToken_thenReturnUnauthorized() throws Exception {
        //given
        AuthenticationTokenCredentials credentials = new AuthenticationTokenCredentials(
                "123",
                Map.of("authority", Authority.USER.name())
        );
        Date start = new Date(System.currentTimeMillis());
        Date expiration = new Date(start.getTime() - 1000);
        String accessToken = factory.generateTokenString(credentials, start, expiration);

        String token = AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX + accessToken;
        //when
        ResultActions actions = mockMvc.perform(get(ApiEndpoints.ME)
                .header(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER, token));
        //then
        actions.andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
        //andDo
        actions.andDo(expiredAccessTokenDocument());
    }

    @Test
    @DisplayName("거절된 AccessToken 으로 요청시 401 응답")
    void givenDeniedAccessToken_thenReturnBadRequest() throws Exception {
        //given
        AuthenticationTokenCredentials credentials = new AuthenticationTokenCredentials("123", Map.of("authority", Authority.USER.name()));

        String accessToken = factory.generateTokenWithInvalidSignature(credentials);

        String token = AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX + accessToken;
        //when
        ResultActions actions = mockMvc.perform(get(ApiEndpoints.ME)
                        .header(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER, token))
                .andDo(print())
                .andDo(expiredAccessTokenDocument());
        //then
        actions.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        //andDo
        actions.andDo(DeniedAccessTokenDocument());
    }


    @Test
    @DisplayName("RefreshToken 으로 토큰 재발급")
    void givenRefreshTokenCookie_thenReturnReissuedTokens() throws Exception {
        //given
        testUserAccountsFactory.initTester();
        final String email = testUserAccountsFactory.TESTER_EMAIL;
        final String password = testUserAccountsFactory.TESTER_RAW_PASSWORD;
        LoginProcess loginProcess = new LoginProcess(email, password);
        AuthenticationToken authenticationToken = accountAuthenticationService.processLogin(loginProcess);
        RefreshToken refreshToken = authenticationToken.refreshToken();
        Cookie cookie = new Cookie(AuthenticationTokenHeaderNames.REFRESH_TOKEN_COOKIE_NAME, refreshToken.getTokenString());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        //when
        ResultActions actions = mockMvc.perform(post(ApiEndpoints.RE_ISSUE_PATH)
                .cookie(cookie));
        //then
        actions.andExpect(status().isOk());
        //andDo
        actions.andDo(reissueDocument());
    }

    private RestDocumentationResultHandler GetMeDocument() {
        return document("userAccount/authentication/getMe",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER).description("Access Token")
                ),
                responseFields(
                        fieldWithPath("userAccountId").type(NUMBER).description("userAccount 식별 아이디"),
                        fieldWithPath("authority").type(STRING).description("권한 정보"),
                        fieldWithPath("lastLoginAt").type(STRING).description("마지막 로그인 시간")
                )
        );
    }

    private static RestDocumentationResultHandler DeniedAccessTokenDocument() {
        return document("userAccount/authentication/deniedAccessToken",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                        fieldWithPath("type").type(STRING).description("에러 타입"),
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("status").type(NUMBER).description("상태"),
                        fieldWithPath("detail").type(STRING).description("상세내용")
                )
        );
    }

    private RestDocumentationResultHandler expiredAccessTokenDocument() {
        return document("userAccount/authentication/expiredAccessToken",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                        fieldWithPath("type").type(STRING).description("에러 타입"),
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("status").type(NUMBER).description("상태"),
                        fieldWithPath("detail").type(STRING).description("상세내용")
                )
        );
    }

    private static RestDocumentationResultHandler reissueDocument() {
        return document("userAccount/authentication/reissue",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
//                        headerWithName(HttpHeaders.COOKIE).description("") // issue ?
                ),
                responseHeaders(
                        headerWithName(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER).description("AccessToken"),
                        headerWithName(HttpHeaders.SET_COOKIE).description("RefreshToken")
                ),
                responseFields(
                        fieldWithPath("userAccountId").type(NUMBER).description("")
                )
        );
    }

    private RestDocumentationResultHandler loginFailDocument() {
        return document("userAccount/authentication/loginFailByEmail",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("email").type(STRING).description("가입되지 않은 이메일"),
                        fieldWithPath("password").type(STRING).description("비밀번호")
                ),
                responseFields(
                        fieldWithPath("type").type(STRING).description("에러 타입"),
                        fieldWithPath("title").type(STRING).description("에러 제목"),
                        fieldWithPath("status").type(NUMBER).description("상태 코드"),
                        fieldWithPath("detail").type(STRING).description("상세 내용"),
                        fieldWithPath("instance").type(STRING).description("요청 리소스 위치")
                )
        );
    }

    private RestDocumentationResultHandler loginSuccessDocument() {
        return document("userAccount/authentication/login",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("email").type(STRING).description("이메일"),
                        fieldWithPath("password").type(STRING).description("비밀번호")
                ),
                responseFields(
                        fieldWithPath("userAccountId").type(NUMBER).description("계정 식별 아이디")
                ),
                responseHeaders(
                        headerWithName(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER).description("AccessToken, 요청시 인증 및 인가 수행"),
                        headerWithName(HttpHeaders.SET_COOKIE).description("RefreshToken, 재발급 토큰")
                )
        );
    }
}
