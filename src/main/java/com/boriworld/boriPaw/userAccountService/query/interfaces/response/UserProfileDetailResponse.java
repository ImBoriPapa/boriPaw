package com.boriworld.boriPaw.userAccountService.query.interfaces.response;

import com.boriworld.boriPaw.userAccountService.query.domain.model.UserProfileDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDetailResponse {

    public Long userAccountId;

    public String username;

    public String nickname;
    public String profileImage;
    public String introduce;
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
                detail.getCountOfPosts(),
                detail.getCountOfFollowers(),
                detail.getCountOfFollows()
        );
    }
}
