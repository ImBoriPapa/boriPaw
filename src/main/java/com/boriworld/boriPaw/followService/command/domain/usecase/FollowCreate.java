package com.boriworld.boriPaw.followService.command.domain.usecase;

import com.boriworld.boriPaw.followService.command.domain.value.Following;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;

public record FollowCreate(Follower follower, Following following) {

}
