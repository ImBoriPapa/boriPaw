package com.boriworld.boriPaw.followService.command.infrastructure.persistence;

import com.boriworld.boriPaw.followService.command.domain.model.Follow;
import com.boriworld.boriPaw.followService.command.domain.repository.FollowRepository;
import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepository {

    private final FollowJpaRepository followJpaRepository;

    @Override
    public boolean existsByFollowerAndFollowing(Follower follower,Following following) {
        return followJpaRepository.findByFollowerAndFollowing(follower.getUserAccountId().getId(),follower.getUserAccountId().getId());
    }

    @Override
    public Follow save(Follow follow) {
        return followJpaRepository.save(FollowEntity.fromModel(follow)).toModel();
    }

    @Override
    public Optional<Follow> findByFollowId(FollowId followId) {
        return followJpaRepository.findByFollowId(followId.getId()).map(FollowEntity::toModel);
    }

    @Override
    public void delete(Follow follow) {
        followJpaRepository.delete(FollowEntity.fromModel(follow));
    }
}
