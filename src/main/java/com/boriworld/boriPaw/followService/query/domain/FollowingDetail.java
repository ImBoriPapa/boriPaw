package com.boriworld.boriPaw.followService.query.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowingDetail {
    private Long followId;
    private Long userAccountId;
    private String username;
    private String nickname;
    private String profileImage;
}
