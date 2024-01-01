package com.boriworld.boriPaw.followService.query.application;

import com.boriworld.boriPaw.followService.command.domain.model.Follow;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.query.domain.FollowerQuery;
import com.boriworld.boriPaw.followService.query.domain.FollowerSlice;
import com.boriworld.boriPaw.followService.query.domain.FollowingQuery;
import com.boriworld.boriPaw.followService.query.domain.FollowingSlice;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class FollowQueryService {

    private final FollowQueryRepository followQueryRepository;

    /**
     * 타켓을 팔로우하고 있는 목록 조회
     */
    @Transactional(readOnly = true)
    public FollowerSlice findFollower(FollowerQuery followerQuery) {

        return followQueryRepository.findFollowersByFollowing(followerQuery);

    }

    /**
     * 타켓이 팔로우 하는 목록 조회
     */
    public FollowingSlice findFollowing(FollowingQuery followingQuery) {

        return followQueryRepository.findFollowingsByFollower(followingQuery);
    }
}
