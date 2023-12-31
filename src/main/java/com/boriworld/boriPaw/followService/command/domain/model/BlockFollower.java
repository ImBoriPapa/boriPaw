package com.boriworld.boriPaw.followService.command.domain.model;

import com.boriworld.boriPaw.followService.command.domain.value.BlockFollowerId;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;

import java.time.LocalDateTime;

public final class BlockFollower {
    private final BlockFollowerId blockFollowerId;
    private final Following following;
    private final Follower follower;
    private final LocalDateTime blockedAt;

    public BlockFollower(BlockFollowerId blockFollowerId, Following following, Follower follower, LocalDateTime blockedAt) {
        this.blockFollowerId = blockFollowerId;
        this.following = following;
        this.follower = follower;
        this.blockedAt = blockedAt;
    }
}
