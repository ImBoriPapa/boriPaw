package com.boriworld.boriPaw.followService.command.infrastructure.persistence;

import com.boriworld.boriPaw.followService.command.domain.FollowServiceUserEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private FollowServiceUserEntity followedUser;
    @ManyToOne
    private FollowServiceUserEntity followerUser;
    private LocalDateTime followedAt;

}
