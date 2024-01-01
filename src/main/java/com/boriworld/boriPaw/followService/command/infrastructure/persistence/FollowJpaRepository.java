package com.boriworld.boriPaw.followService.command.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowJpaRepository extends JpaRepository<FollowEntity,Long> {
    Optional<FollowEntity> findByFollowId(Long id);

    boolean existsByFollowerAndFollowing(Long followerId, Long followingId);
}
