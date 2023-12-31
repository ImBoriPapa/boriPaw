package com.boriworld.boriPaw.followService.command.domain.model;

import com.boriworld.boriPaw.followService.command.domain.usecase.FollowCreate;
import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "followId")
public final class Follow {
    private final FollowId followId;
    private final Following following;
    private final Follower follower;
    private final LocalDateTime followedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Follow(FollowId followId, Following following, Follower follower, LocalDateTime followedAt) {
        this.followId = followId;
        this.following = following;
        this.follower = follower;
        this.followedAt = followedAt;
    }

    public static Follow follow(FollowCreate followCreate) {
        return Follow.builder()
                .follower(followCreate.follower())
                .following(followCreate.following())
                .followedAt(LocalDateTime.now())
                .build();
    }
}
