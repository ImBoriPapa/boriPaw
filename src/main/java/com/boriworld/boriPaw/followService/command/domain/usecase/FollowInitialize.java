package com.boriworld.boriPaw.followService.command.domain.usecase;

import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;

import java.time.LocalDateTime;

public record FollowInitialize(
        FollowId followId,
        Following following,
        Follower follower,
        LocalDateTime followedAt
) {
}
