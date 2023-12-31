package com.boriworld.boriPaw.followService.command.domain.repository;


import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;



public interface BlockFollowerRepository {
    boolean existsByFollowingAndFollower(Following following, Follower follower);
}
