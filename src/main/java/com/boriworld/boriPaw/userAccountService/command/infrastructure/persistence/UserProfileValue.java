package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileValue {
    private String nickname;
    private String profileImage;
    private String introduce;

    protected UserProfileValue(String nickname, String profileImage, String introduce) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduce = introduce;
    }

    public static UserProfileValue fromUserProfile(UserProfile userProfile) {
        return new UserProfileValue(userProfile.getNickname(), userProfile.getProfileImage(), userProfile.getIntroduce());
    }

    public UserProfile toUserProfile() {
        return UserProfile.of(this.nickname, this.profileImage, this.introduce);
    }
}
