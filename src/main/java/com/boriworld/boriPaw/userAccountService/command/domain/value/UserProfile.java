package com.boriworld.boriPaw.accountService.command.domain.value;

public final class UserProfile {
    private final String nickname;
    private final String profileImage;

    private UserProfile(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static UserProfile of(String nickname, String profileImage) {
        return new UserProfile(nickname, profileImage);
    }
}
