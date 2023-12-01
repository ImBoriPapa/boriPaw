package com.boriworld.boriPaw.followService.command.domain;

public record FollowCreate(
        AccountUserId accountUserId,
        Following following,
        Follower follower
) {

}
