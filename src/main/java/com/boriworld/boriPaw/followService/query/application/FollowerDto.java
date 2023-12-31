package com.boriworld.boriPaw.followService.query.application;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowerDto {
    private Long id;
    private String nickname;
    private String profileImage;

    @Builder
    public FollowerDto(Long id, String nickname, String profileImage) {
        this.id = id;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
