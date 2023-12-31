package com.boriworld.boriPaw.followService.query.application;

import com.boriworld.boriPaw.followService.command.domain.model.Follow;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;

import java.util.List;

public interface FollowQueryRepository {
    List<Follow> findByFollowing(Following following);
    List<Follow> findByFollower(Follower follower);
}
