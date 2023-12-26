package com.boriworld.boriPaw.userAccountService.command.domain.value;


import lombok.Getter;


@Getter
public final class UserProfile {
    private final String nickname;
    private final String profileImage;
    private final String introduce;

    private UserProfile(String nickname, String profileImage, String introduce) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduce = introduce;
    }

    public static UserProfile of(final String nickname, final String profileImage, final String introduce) {
        return new UserProfile(nickname, profileImage, introduce);
    }
}
