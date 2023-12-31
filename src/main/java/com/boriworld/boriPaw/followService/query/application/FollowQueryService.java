package com.boriworld.boriPaw.followService.query.application;

import com.boriworld.boriPaw.followService.command.domain.model.Follow;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FollowQueryService {

    private final FollowQueryRepository followQueryRepository;

    /**
     * 타켓을 팔로우하고 있는 목록 조회
     */
    public List<Follow> findFollower(Following following) {

        return followQueryRepository.findByFollowing(following);

    }

    /**
     * 타켓이 팔로우 하는 목록 조회
     */
    public List<Follow> findFollowing(Follower follower) {

        return followQueryRepository.findByFollower(follower);
    }
}
