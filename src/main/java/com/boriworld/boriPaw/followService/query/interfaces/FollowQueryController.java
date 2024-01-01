package com.boriworld.boriPaw.followService.query.interfaces;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.followService.command.domain.value.Following;
import com.boriworld.boriPaw.followService.query.application.FollowQueryService;
import com.boriworld.boriPaw.followService.query.domain.FollowerQuery;
import com.boriworld.boriPaw.followService.query.domain.FollowerSlice;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class FollowQueryController {

    private final FollowQueryService followQueryService;

    @GetMapping(ApiEndpoints.GET_FOLLOWER)
    public ResponseEntity<FollowerSlice> getFollower(@AuthenticationPrincipal UserAccountPrincipal principal,
                                                     @RequestParam(defaultValue = "0") Long last) {
        Following following = Following.of(principal.userAccountId().getId());

        FollowerSlice followerSlice = followQueryService.findFollower(new FollowerQuery(following, last));

        return ResponseEntity
                .ok()
                .body(followerSlice);
    }

    @GetMapping(ApiEndpoints.GET_FOLLOWING)
    public ResponseEntity getFollowing(@AuthenticationPrincipal UserAccountPrincipal principal) {


        return null;
    }
}
