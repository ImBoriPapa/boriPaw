package com.boriworld.boriPaw.userAccountService.query.interfaces.controller;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;

import com.boriworld.boriPaw.followService.query.domain.value.Requester;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountPrincipal;
import com.boriworld.boriPaw.userAccountService.command.domain.model.Relationship;
import com.boriworld.boriPaw.userAccountService.command.domain.model.RelationshipSubject;
import com.boriworld.boriPaw.userAccountService.command.domain.model.RelationshipTarget;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.application.UserAccountQueryService;

import com.boriworld.boriPaw.userAccountService.query.domain.model.UserProfileDetail;
import com.boriworld.boriPaw.userAccountService.query.interfaces.response.UserProfileDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAccountQueryController {
    private final UserAccountQueryService userAccountQueryService;


    @GetMapping(ApiEndpoints.GET_PROFILE)
    public ResponseEntity<UserProfileDetailResponse> getProfile(@PathVariable(value = "user-accountsId") Long userAccountId,
                                                                @AuthenticationPrincipal UserAccountPrincipal principal) {
        log.info("Get Profile userAccountId: {}", userAccountId);

        UserProfileDetail userProfileDetail = userAccountQueryService.getUserDetail(Requester.of(principal.userAccountId()), UserAccountId.of(userAccountId));

        return ResponseEntity.ok()
                .body(UserProfileDetailResponse.from(userProfileDetail));
    }
}
