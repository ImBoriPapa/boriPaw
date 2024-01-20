package com.boriworld.boriPaw.mediumTest.restdocsTest;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.common.constant.AuthenticationTokenHeaderNames;
import com.boriworld.boriPaw.testContainer.testcontainer.RestDocsMediumTest;
import com.boriworld.boriPaw.testData.TestUserAccountsFactory;
import com.boriworld.boriPaw.userAccountService.command.application.UserAuthenticationService;
import com.boriworld.boriPaw.userAccountService.command.application.dto.LoginProcess;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationToken;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.interfaces.controller.UserIntroduceChangeRequest;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.UserNicknameChangeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAccountProfileDocsTest extends RestDocsMediumTest {

    @Autowired
    TestUserAccountsFactory userAccountsFactory;
    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Test
    @DisplayName("Get UserProfile")
    void 유저_프로필_조회() throws Exception {
        //given
        UserAccount userAccount = userAccountsFactory.initTester();
        AuthenticationToken authenticationToken = userAuthenticationService.processLogin(new LoginProcess(userAccountsFactory.TESTER_EMAIL, userAccountsFactory.TESTER_RAW_PASSWORD));

        //when
        ResultActions actions = mockMvc.perform(get(ApiEndpoints.GET_PROFILE, userAccount.getUserAccountId().getId())
                .header(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER, AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX + authenticationToken.accessToken().getTokenString()));
        //then
        actions.andDo(print());
        //andDo
        actions.andDo(document("userAccount/profile/get-profile",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                        fieldWithPath("userAccountId").type(JsonFieldType.NUMBER).description("Id"),
                        fieldWithPath("username").type(JsonFieldType.STRING).description("username"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("nickname"),
                        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("profileImage").optional(),
                        fieldWithPath("introduce").type(JsonFieldType.STRING).description("introduce"),
                        fieldWithPath("hasFollowing").type(JsonFieldType.BOOLEAN).description("hasFollow"),
                        fieldWithPath("countOfPosts").type(JsonFieldType.NUMBER).description("countOfPosts"),
                        fieldWithPath("countOfFollowers").type(JsonFieldType.NUMBER).description("countOfFollowers"),
                        fieldWithPath("countOfFollows").type(JsonFieldType.NUMBER).description("countOfFollows")
                )
        ));
    }

    @Test
    @DisplayName("Patch UserProfile Nickname Success Return 200")
    void 유저_닉네임_변경() throws Exception {
        //given
        UserAccount userAccount = userAccountsFactory.initTester();
        AuthenticationToken authenticationToken = userAuthenticationService.processLogin(new LoginProcess(userAccountsFactory.TESTER_EMAIL, userAccountsFactory.TESTER_RAW_PASSWORD));
        UserNicknameChangeRequest request = new UserNicknameChangeRequest("updatedNickname");
        //when
        ResultActions actions = mockMvc.perform(patch(ApiEndpoints.CHANGE_PROFILE_NICKNAME, userAccount.getUserAccountId().getId())
                .header(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER, AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX + authenticationToken.accessToken().getTokenString())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        //then
        actions.andDo(print());
        //andDo
        actions.andDo(document("userAccount/profile/change-nickname",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임")
                ),
                responseFields(
                        fieldWithPath("userAccountId").type(JsonFieldType.NUMBER).description("유저 계정 식별 아이디")
                )
        ));
    }

    @Test
    @DisplayName("Patch UserProfile Nickname Fail No Resource 403")
    void 유저_프로필_사용자가_아니라서_닉네임_변경_실패() throws Exception {
        //given
        UserAccount userAccount = userAccountsFactory.initTester();
        AuthenticationToken authenticationToken = userAuthenticationService.processLogin(new LoginProcess(userAccountsFactory.TESTER_EMAIL, userAccountsFactory.TESTER_RAW_PASSWORD));
        UserNicknameChangeRequest request = new UserNicknameChangeRequest("updatedNickname");
        //when
        ResultActions actions = mockMvc.perform(patch(ApiEndpoints.CHANGE_PROFILE_NICKNAME, userAccount.getUserAccountId().getId() + 1)
                .header(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER, AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX + authenticationToken.accessToken().getTokenString())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        //then
        actions.andDo(print());
        actions.andExpect(status().isForbidden());
        //andDo
        actions.andDo(document("userAccount/profile/change-nickname-fail-no-resource-update-privileges",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임")
                ),
                responseFields(
                        fieldWithPath("type").type(JsonFieldType.STRING).description("type"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                        fieldWithPath("detail").type(JsonFieldType.STRING).description("detail"),
                        fieldWithPath("instance").type(JsonFieldType.STRING).description("instance")
                )
        ));
    }

    @Test
    @DisplayName("새로운 닉네임이 null 이면 400 응답")
    void givenNullNickname_thenReturn400() throws Exception {
        //given
        UserAccount userAccount = userAccountsFactory.initTester();
        AuthenticationToken authenticationToken = userAuthenticationService.processLogin(new LoginProcess(userAccountsFactory.TESTER_EMAIL, userAccountsFactory.TESTER_RAW_PASSWORD));
        UserNicknameChangeRequest request = new UserNicknameChangeRequest(null);
        //when
        ResultActions actions = mockMvc.perform(patch(ApiEndpoints.CHANGE_PROFILE_NICKNAME, userAccount.getUserAccountId().getId())
                .header(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER, AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX + authenticationToken.accessToken().getTokenString())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        //then
        actions.andDo(print());
        actions.andExpect(status().isBadRequest());
        //andDo
        actions.andDo(document("userAccount/profile/change-nickname-fail-null-nickname",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("nickname").type(JsonFieldType.NULL).description("변경할 닉네임")
                ),
                responseFields(
                        fieldWithPath("type").type(JsonFieldType.STRING).description("type"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                        fieldWithPath("detail").type(JsonFieldType.STRING).description("detail"),
                        fieldWithPath("instance").type(JsonFieldType.STRING).description("instance")
                )
        ));
    }

    @Test
    @DisplayName("새로운 닉네임에 형식이 맞지 않으면 400 응답")
    void givenInvalidNickname_thenReturn400() throws Exception {
        //given
        UserAccount userAccount = userAccountsFactory.initTester();
        AuthenticationToken authenticationToken = userAuthenticationService.processLogin(new LoginProcess(userAccountsFactory.TESTER_EMAIL, userAccountsFactory.TESTER_RAW_PASSWORD));
        UserNicknameChangeRequest request = new UserNicknameChangeRequest("123");
        //when
        ResultActions actions = mockMvc.perform(patch(ApiEndpoints.CHANGE_PROFILE_NICKNAME, userAccount.getUserAccountId().getId())
                .header(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER, AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX + authenticationToken.accessToken().getTokenString())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        //then
        actions.andDo(print());
        actions.andExpect(status().isBadRequest());
        //andDo
        actions.andDo(document("userAccount/profile/change-nickname-fail-invalid-nickname",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임")
                ),
                responseFields(
                        fieldWithPath("type").type(JsonFieldType.STRING).description("type"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                        fieldWithPath("detail").type(JsonFieldType.STRING).description("detail"),
                        fieldWithPath("instance").type(JsonFieldType.STRING).description("instance")
                )
        ));
    }

    @Test
    @DisplayName("자기소개 수정 성공")
    void givenNewIntroduce_thenReturn200() throws Exception {
        //given
        UserAccount userAccount = userAccountsFactory.initTester();
        AuthenticationToken authenticationToken = userAuthenticationService.processLogin(new LoginProcess(userAccountsFactory.TESTER_EMAIL, userAccountsFactory.TESTER_RAW_PASSWORD));
        UserIntroduceChangeRequest request = new UserIntroduceChangeRequest("This is new introduce");
        //when
        ResultActions actions = mockMvc.perform(patch(ApiEndpoints.CHANGE_PROFILE_INTRODUCE, userAccount.getUserAccountId().getId())
                .header(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER, AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX + authenticationToken.accessToken().getTokenString())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        //then
        actions.andDo(print());
        actions.andExpect(status().isOk());
        //andDo
        actions.andDo(document("userAccount/profile/change-introduce",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("introduce").type(JsonFieldType.STRING).description("변경할 닉네임")
                ),
                responseFields(
                        fieldWithPath("userAccountId").type(JsonFieldType.NUMBER).description("유저 계정 식별 아이디")
                )
        ));
    }
}
