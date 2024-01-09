package com.boriworld.boriPaw.followService.query.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Followings {

    private Long last;
    private Boolean hasNext;
    private List<FollowerDetail> followings;


}
