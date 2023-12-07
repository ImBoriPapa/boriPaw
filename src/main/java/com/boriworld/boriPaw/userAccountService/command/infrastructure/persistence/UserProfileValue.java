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

    public UserProfileValue(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static UserProfileValue from(UserProfile userProfile) {
        return new UserProfileValue(userProfile.getNickname(), userProfile.getProfileImage());
    }

    public UserProfile to() {
        return UserProfile.of(this.nickname, this.profileImage);
    }
}
