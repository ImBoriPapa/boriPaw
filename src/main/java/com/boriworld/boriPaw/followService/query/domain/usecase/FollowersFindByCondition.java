package com.boriworld.boriPaw.followService.query.domain.usecase;

import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.followService.command.domain.value.Following;
import com.boriworld.boriPaw.followService.query.domain.value.Requester;

public record FollowersFindByCondition(
        Following following,
        Requester requester,
        FollowId followId,
        Integer size,
        int limit) {
}
