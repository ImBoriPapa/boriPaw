package com.boriworld.boriPaw.userAccountService.query.interfaces.controller;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.application.UserAccountQueryService;

import com.boriworld.boriPaw.userAccountService.query.domain.model.UserProfileDetail;
import com.boriworld.boriPaw.userAccountService.query.interfaces.response.UserProfileDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAccountQueryController {
    private final UserAccountQueryService userAccountQueryService;

    @GetMapping(ApiEndpoints.GET_PROFILE)
    public ResponseEntity<UserProfileDetailResponse> getProfile(@PathVariable Long userAccountId) {
        log.info("Get Profile userAccountId: {}", userAccountId);
        UserProfileDetail userProfileDetail = userAccountQueryService.getUserDetail(UserAccountId.of(userAccountId));

        return ResponseEntity.ok()
                .body(UserProfileDetailResponse.from(userProfileDetail));
    }
}
