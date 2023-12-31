package com.boriworld.boriPaw.followService.command.domain.model;

import com.boriworld.boriPaw.followService.command.domain.usecase.FollowCreate;
import com.boriworld.boriPaw.followService.command.domain.usecase.FollowInitialize;
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

    public static Follow from(FollowCreate followCreate) {
        return Follow.builder()
                .follower(followCreate.follower())
                .following(followCreate.following())
                .followedAt(LocalDateTime.now())
                .build();
    }

    /**
     * (warn)
     * persistence 계층에 Entity 와 Model 을 맵핑 하기 위한 객체입니다. 다른 용도로 사용해서는 안됩니다.
     */
    public static Follow initialize(FollowInitialize initialize) {
        return Follow.builder()
                .followId(initialize.followId())
                .following(initialize.following())
                .follower(initialize.follower())
                .followedAt(initialize.followedAt())
                .build();
    }
}
