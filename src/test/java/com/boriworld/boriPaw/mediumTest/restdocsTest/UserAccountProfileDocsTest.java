package com.boriworld.boriPaw.mediumTest.restdocsTest;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.common.constant.AuthenticationTokenHeaderNames;
import com.boriworld.boriPaw.testContainer.testcontainer.RestDocsMediumTest;
import com.boriworld.boriPaw.testData.TestUserAccountsFactory;
import com.boriworld.boriPaw.userAccountService.command.application.UserAuthenticationService;
import com.boriworld.boriPaw.userAccountService.command.application.dto.LoginProcess;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationToken;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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

}
