package com.boriworld.boriPaw.followService.command.domain;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 팔로우 기능 Root 애그리거트
 */
public final class Follow {
    private final Long id;
    private final AccountUserId accountUserId;
    //ManyToOne
    private final Following following;
    //ManyToOne
    private final Follower follower;
    private final LocalDateTime followedAt;

    @Builder
    private Follow(Long id, AccountUserId accountUserId, Following following, Follower follower, LocalDateTime followedAt) {
        this.id = id;
        this.accountUserId = accountUserId;
        this.following = following;
        this.follower = follower;
        this.followedAt = followedAt;
    }

    public static Follow of(FollowCreate followCreate) {
        return Follow.builder()
                .accountUserId(followCreate.accountUserId())
                .following(followCreate.following())
                .follower(followCreate.follower())
                .followedAt(LocalDateTime.now())
                .build();
    }
}
