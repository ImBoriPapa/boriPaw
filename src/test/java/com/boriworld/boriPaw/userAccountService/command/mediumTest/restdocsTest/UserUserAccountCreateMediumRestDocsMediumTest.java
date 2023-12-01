package com.boriworld.boriPaw.accountService.command.mediumTest.restdocsTest;

import com.boriworld.boriPaw.accountService.command.interfaces.request.AccountCreateRequest;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.testContainer.testcontainer.RestDocsMediumTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;

import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserAccountCreateMediumRestDocsMediumTest extends RestDocsMediumTest {
    @Test
    @DisplayName("회원 생성 통합 테스트: 회원 생성 요청 성공 통합 테스트")
    void 회원생성_요청_성공() throws Exception {
        //given
        AccountCreateRequest request = new AccountCreateRequest("email@email.com", "sampleName", "password1234!@", "nickname");

        String path = ApiEndpoints.ACCOUNTS_ROOT_PATH;
        //when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        );
        //then
        resultActions
                .andExpect(status().isCreated())
                .andDo(document("Account/create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("accountName").type(JsonFieldType.STRING).description("계정명"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("계정 아이디")
                        )
                ));
    }
}
