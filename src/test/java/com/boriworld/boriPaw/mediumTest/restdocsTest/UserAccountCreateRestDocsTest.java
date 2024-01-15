package com.boriworld.boriPaw.mediumTest.restdocsTest;


import com.boriworld.boriPaw.userAccountService.command.interfaces.request.UserAccountCreateRequest;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.testContainer.testcontainer.RestDocsMediumTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;


import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserAccountCreateRestDocsTest extends RestDocsMediumTest {
    @Test
    @DisplayName("계정 생성 성공시 201 응답")
    void givenUserCreateRequest_thenCreateUserAccountAndReturnAccountId() throws Exception {
        //given
        final String email = "email@email.com";
        final String password = "password1234";
        final String nickname = "nickname";
        UserAccountCreateRequest request = new UserAccountCreateRequest(email, password, nickname);
        //when
        ResultActions actions = mockMvc.perform(post(ApiEndpoints.ACCOUNTS_ROOT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)));
        //then
        actions.andExpectAll(
                status().isCreated(),
                header().exists(HttpHeaders.LOCATION),
                jsonPath("$.userAccountId").exists()
        );
        //andDp
        actions.andDo(createUserAccountDocument());
    }

    @Test
    @DisplayName("계정생성시 검증에 문제가 있을 경우 400 응답")
    void givenUserAccountCreateRequestWithInvalidation_then_Return400() throws Exception {
        //given
        UserAccountCreateRequest request = new UserAccountCreateRequest();
        //when
        ResultActions actions = mockMvc.perform(post(ApiEndpoints.ACCOUNTS_ROOT_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(request)));
        //then
        actions.andDo(print());
        actions.andDo(document("userAccount/management/create-validation-fail",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.NULL).description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.NULL).description("비밀번호"),
                        fieldWithPath("nickname").type(JsonFieldType.NULL).description("닉네임")),
                responseFields(
                        fieldWithPath("type").type(JsonFieldType.STRING).description("type"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                        fieldWithPath("detail").type(JsonFieldType.STRING).description("detail"),
                        fieldWithPath("instance").type(JsonFieldType.STRING).description("instance")
                )
                ));
    }

    private static RestDocumentationResultHandler createUserAccountDocument() {
        return document("userAccount/management/create",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")),
                responseFields(
                        fieldWithPath("userAccountId").type(JsonFieldType.NUMBER).description("유저 계정 아이디")
                ));
    }

}
