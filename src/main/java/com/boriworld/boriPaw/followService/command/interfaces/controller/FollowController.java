package com.boriworld.boriPaw.followService.command.interfaces.controller;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.followService.command.application.FollowService;
import com.boriworld.boriPaw.followService.command.domain.usecase.FollowCreate;
import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;
import com.boriworld.boriPaw.followService.command.interfaces.dto.FollowCreateRequest;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FollowController {
    private final FollowService followService;

    @PostMapping(ApiEndpoints.FOLLOW_ROOT_PATH)
    public ResponseEntity<FollowId> follow(@RequestBody FollowCreateRequest followCreateRequest, @AuthenticationPrincipal UserAccountPrincipal principal) {

        FollowId followId = followService.processFollow(new FollowCreate(Follower.of(principal.userAccountId().getId()), Following.of(followCreateRequest.getFollowingId())));

        return ResponseEntity
                .created(URI.create(ApiEndpoints.FOLLOW_ROOT_PATH + "/" + followId.getId()))
                .body(followId);
    }

    @DeleteMapping(ApiEndpoints.UNFOLLOW)
    public ResponseEntity<Void> unFollow(@PathVariable Long followId) {

        followService.processUnFollow(FollowId.of(followId));

        return ResponseEntity
                .noContent()
                .build();
    }
}
