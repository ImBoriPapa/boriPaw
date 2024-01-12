package com.boriworld.boriPaw.mediumTest.restdocsTest;


import com.boriworld.boriPaw.userAccountService.command.interfaces.request.UserAccountCreateRequest;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.testContainer.testcontainer.RestDocsMediumTest;

import org.junit.jupiter.api.Test;


import org.springframework.http.MediaType;


import org.springframework.restdocs.payload.JsonFieldType;


import org.springframework.test.web.servlet.ResultActions;


import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAccountCreateRestDocsTest extends RestDocsMediumTest {
    @Test
    void givenUserAccountRequest_thenCreateUserAccountAndReturnAccountId() throws Exception {
        //given
        final String email = "email@email.com";
        final String password = "password1234";
        final String nickname = "nickname";
        UserAccountCreateRequest request = new UserAccountCreateRequest(email, password, nickname);
        //when
        ResultActions resultActions = mockMvc.perform(post(ApiEndpoints.ACCOUNTS_ROOT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)));
        //then
        resultActions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("userAccount/management/create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")),
                        responseFields(
                                fieldWithPath("userAccountId").type(JsonFieldType.NUMBER).description("유저 계정 아이디")
                        )));
    }

}
