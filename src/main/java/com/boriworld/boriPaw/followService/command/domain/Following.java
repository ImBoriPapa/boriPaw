package com.boriworld.boriPaw.followService.command.domain;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

public final class Following {
    private final Long followingId;
    private final UserInformation userInformation;
    private final LocalDateTime followingAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Following(Long followingId, UserInformation userInformation, LocalDateTime followingAt) {
        this.followingId = followingId;
        this.userInformation = userInformation;
        this.followingAt = followingAt;
    }

    public Following of(FollowingCreate create) {
        return Following.builder()
                .userInformation(create.userInformation())
                .build();
    }
}
