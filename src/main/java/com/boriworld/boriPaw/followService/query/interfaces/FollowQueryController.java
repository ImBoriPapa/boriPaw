package com.boriworld.boriPaw.followService.query.interfaces;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.followService.command.domain.value.Following;
import com.boriworld.boriPaw.followService.query.application.FollowQueryService;
import com.boriworld.boriPaw.followService.query.domain.usecase.FollowersFindByCondition;
import com.boriworld.boriPaw.followService.query.domain.model.Followers;
import com.boriworld.boriPaw.followService.query.domain.value.Requester;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FollowQueryController {
    private final FollowQueryService followQueryService;

    /**
     * Using Value Path
     * GET /follow/followers?userAccountId=123 : userAccountId=123 를 팔로우하는 목록
     */
    @GetMapping(ApiEndpoints.GET_FOLLOWERS)
    public ResponseEntity<Followers> getFollowers(FollowersSearchParameters parameters,
                                                  @AuthenticationPrincipal UserAccountPrincipal principal) {
        log.info("userAccountId: {}", parameters.getUserAccountId());
        log.info("followId: {}", parameters.getFollowId());
        Following following = Following.of(parameters.getUserAccountId());
        Requester requester = Requester.of(principal.userAccountId());
        FollowId followId = FollowId.of(parameters.getFollowId());
        Followers followers = followQueryService.findFollowers(new FollowersFindByCondition(following, requester, followId, 10, 10));

        return ResponseEntity
                .ok()
                .body(followers);
    }

    /**
     * Using Value Path
     * GET /follow/followings?userAccountId=123 : userAccountId=123 가 팔로우하는 목록
     */
    @GetMapping(ApiEndpoints.GET_FOLLOWINGS)
    public ResponseEntity<Followers> getFollowings() {

        return ResponseEntity
                .ok()
                .body(null);
    }
}
