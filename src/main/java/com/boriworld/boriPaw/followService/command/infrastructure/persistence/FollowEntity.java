package com.boriworld.boriPaw.followService.command.domain;

import java.time.LocalDateTime;

public final class Follow {
    private String id;
    private FollowServiceUser followedUser;
    private FollowServiceUser followerUser;
    private LocalDateTime followedAt;

    private Follow(String id, FollowServiceUser followedUser, FollowServiceUser followerUser, LocalDateTime followedAt) {
        this.id = id;
        this.followedUser = followedUser;
        this.followerUser = followerUser;
        this.followedAt = followedAt;
    }

    public static Follow of() {
        return new Follow(null, null, null, null);
    }
}
