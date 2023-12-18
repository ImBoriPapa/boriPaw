package com.boriworld.boriPaw.userAccountService.query.interfaces;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountPrincipal;
import com.boriworld.boriPaw.userAccountService.query.application.UserAccountQueryService;

import com.boriworld.boriPaw.userAccountService.query.domain.model.UserInformation;
import com.boriworld.boriPaw.userAccountService.query.interfaces.response.UserInformationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;




@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAccountQueryController {

    private final UserAccountQueryService userAccountQueryService;

    @GetMapping(ApiEndpoints.ME)
    public ResponseEntity<UserInformationResponse> getMe(@AuthenticationPrincipal UserAccountPrincipal principal) {

        UserInformation userInformation = userAccountQueryService.getUserInformationBy(principal.userAccountId());
        return ResponseEntity.ok()
                .body(UserInformationResponse.from(userInformation));
    }
}
