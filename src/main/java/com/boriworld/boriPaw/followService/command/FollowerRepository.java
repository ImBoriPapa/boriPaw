package com.boriworld.boriPaw.followService.command;

import com.boriworld.boriPaw.followService.command.domain.Follower;

public interface FollowerRepository {

    Follower save(Follower follower);
}
