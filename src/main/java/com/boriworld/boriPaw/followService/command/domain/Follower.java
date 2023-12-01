package com.boriworld.boriPaw.followService.command.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class Follower {
    private final Long followerId;
    private final UserInformation userInformation;
    private final LocalDateTime followedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Follower(Long followerId, UserInformation userInformation, LocalDateTime followedAt) {
        this.followerId = followerId;
        this.userInformation = userInformation;
        this.followedAt = followedAt;
    }

    public static Follower of(UserInformation userInformation) {
        return Follower.builder()
                .userInformation(userInformation)
                .followedAt(LocalDateTime.now())
                .build();
    }
}
