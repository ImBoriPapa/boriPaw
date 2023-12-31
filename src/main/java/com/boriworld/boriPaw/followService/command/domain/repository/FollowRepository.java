package com.boriworld.boriPaw.followService.command.domain.repository;

import com.boriworld.boriPaw.followService.command.domain.model.Follow;
import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;

import java.util.List;
import java.util.Optional;

public interface FollowRepository {
    boolean existsByFollowerAndFollowing(Following following, Follower follower);

    Follow save(Follow follow);
    List<Follow> findAllByFollowing(Following following);
    Optional<Follow> findByFollowId(FollowId followId);
    void delete(Follow follow);
}
