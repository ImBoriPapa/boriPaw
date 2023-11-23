package com.boriworld.boriPaw.accountService.command.mediumTest.restdocsTest;

import com.boriworld.boriPaw.accountService.command.interfaces.request.AccountCreateRequest;

import com.boriworld.boriPaw.testContainer.testcontainer.RestDocsMediumTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;

import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AccountCreateMediumRestDocsMediumTest extends RestDocsMediumTest {
    @Test
    @DisplayName("회원 생성 통합 테스트: 회원 생성 요청 성공 통합 테스트")
    void 회원생성_요청_성공() throws Exception {
        //given
        AccountCreateRequest request = new AccountCreateRequest("email@email.com", "sampleName", "password1234!@", "nickname");


        //when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        );
        //then
        resultActions
                .andExpect(status().isCreated())
                .andDo(document("Account/create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(),
                        responseFields()
                ));
    }
}
