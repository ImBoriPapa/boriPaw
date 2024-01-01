package com.boriworld.boriPaw.followService.query.domain;

import com.boriworld.boriPaw.followService.command.domain.value.Following;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowingQuery {
    private Following following;
    private Long last;
}
