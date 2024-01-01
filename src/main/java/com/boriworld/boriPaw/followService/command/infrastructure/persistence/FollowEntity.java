package com.boriworld.boriPaw.followService.command.infrastructure.persistence;

import com.boriworld.boriPaw.followService.command.domain.model.Follow;
import com.boriworld.boriPaw.followService.command.domain.usecase.FollowInitialize;
import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "follow",
        indexes = {
                @Index(name = "idx_following", columnList = "following"),
                @Index(name = "idx_follower", columnList = "follower")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;
    private Long following;
    private Long follower;
    private LocalDateTime followedAt;

    protected FollowEntity(Long followId, Long following, Long follower, LocalDateTime followedAt) {
        this.followId = followId;
        this.following = following;
        this.follower = follower;
        this.followedAt = followedAt;
    }

    public Follow toModel() {
        return Follow.initialize(new FollowInitialize(
                FollowId.of(this.followId),
                Following.of(this.following),
                Follower.of(this.follower),
                this.followedAt));
    }

    public static FollowEntity fromModel(Follow follow) {
        return new FollowEntity(
                follow.getFollowId() == null ? null : follow.getFollowId().getId(),
                follow.getFollowing().getUserAccountId().getId(),
                follow.getFollower().getUserAccountId().getId(),
                follow.getFollowedAt());
    }
}
