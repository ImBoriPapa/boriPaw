package com.boriworld.boriPaw.followService.query.application;

import com.boriworld.boriPaw.followService.query.domain.usecase.FollowersFindByCondition;
import com.boriworld.boriPaw.followService.query.domain.model.Followers;

import com.boriworld.boriPaw.followService.query.domain.repository.FollowQueryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class FollowQueryService {

    private final FollowQueryRepository followQueryRepository;

    /**
     * 타켓을 팔로우하고 있는 목록 조회
     */
    @Transactional(readOnly = true)
    public Followers findFollowers(FollowersFindByCondition followersFindByCondition) {

        return followQueryRepository.findFollowersByCondition(followersFindByCondition);

    }

}
