package com.boriworld.boriPaw.followService.query.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowersSearchParameters {
    private Long followId;
    private Long userAccountId;

}
