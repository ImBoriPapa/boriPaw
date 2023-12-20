package com.boriworld.boriPaw.mediumTest.restdocsTest;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.common.constant.AuthenticationTokenHeaders;
import com.boriworld.boriPaw.testContainer.testcontainer.RestDocsMediumTest;
import com.boriworld.boriPaw.testData.TestUserAccounts;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class UserAccountAuthenticationRestDocsTest extends RestDocsMediumTest {
    @Autowired
    TestUserAccounts testUserAccounts;

    @BeforeEach
    void before() {
        testUserAccounts.init();
    }

    @Test
    void loginTest() throws Exception {
        //given
        final String email = "tester1@test.com";
        final String password = "password1234!@";
        LoginRequest request = new LoginRequest(email, password);
        //when
        ResultActions actions = mockMvc.perform(post(ApiEndpoints.LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)));
        //then
        actions
                .andDo(print())
                .andDo(document("userAccount/authentication/login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description(""),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("")
                        ),
                        responseFields(
                                fieldWithPath("userAccountId").type(JsonFieldType.NUMBER).description("")
                        ),
                        responseHeaders(
                                headerWithName(AuthenticationTokenHeaders.AUTHORIZATION_HEADER).description(""),
                                headerWithName(HttpHeaders.SET_COOKIE).description("")
                        )
                ));

    }
}
