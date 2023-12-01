package com.boriworld.boriPaw.followService.command;

import com.boriworld.boriPaw.followService.command.domain.Follow;

public interface FollowRepository {

    Follow save(Follow follow);
}
