package com.boriworld.boriPaw.followService.query.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FollowingSlice {

    private Long last;
    private Boolean hasNext;
    private List<FollowingDetail> followings;


}
