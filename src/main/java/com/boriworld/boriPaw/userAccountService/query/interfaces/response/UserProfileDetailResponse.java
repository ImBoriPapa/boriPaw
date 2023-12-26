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

    public static UserProfileDetailResponse from(UserProfileDetail detail) {
        return new UserProfileDetailResponse(
                detail.getUserAccountId(),
                detail.getUsername(),
                detail.getNickname(),
                detail.getProfileImage(),
                detail.getIntroduce()
        );
    }
}
