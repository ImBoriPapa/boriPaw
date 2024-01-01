package com.boriworld.boriPaw.followService.query.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowerSlice {
    private Long last;
    private Boolean hasNext;
    private List<FollowerDetail> followers;

}
