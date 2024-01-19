package com.boriworld.boriPaw.userAccountService.query.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDetail {
    private Long userAccountId;
    private String username;
    private String nickname;
    private String profileImage;
    private String introduce;
    private Boolean hasFollowing;
    private Integer countOfPosts;
    private Integer countOfFollowers;
    private Integer countOfFollows;


}
