package com.boriworld.boriPaw.userAccountService.query.interfaces.response;

import com.boriworld.boriPaw.userAccountService.query.domain.model.UserProfileDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDetailResponse {
    private Long userAccountId;
    private String username;
    private String nickname;
    private String profileImage;
    private String introduce;
    private boolean hasFollowing;
    private Integer countOfPosts;
    private Integer countOfFollowers;
    private Integer countOfFollows;

    public static UserProfileDetailResponse from(UserProfileDetail detail) {
        return new UserProfileDetailResponse(
                detail.getUserAccountId(),
                detail.getUsername(),
                detail.getNickname(),
                detail.getProfileImage(),
                detail.getIntroduce(),
                detail.getHasFollowing(),
                detail.getCountOfPosts(),
                detail.getCountOfFollowers(),
                detail.getCountOfFollows()
        );
    }
}
