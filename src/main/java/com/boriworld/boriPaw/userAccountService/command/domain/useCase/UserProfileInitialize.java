package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.value.ProfileImage;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfileId;
import com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.UserProfileEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class UserProfileInitialize {
    private final UserProfileId userProfileId;
    private final UserAccount userAccount;
    private final String nickname;
    private final ProfileImage profileImage;
    private final String introduce;
    private final Integer countOfPosts;
    private final Integer countOfFollowers;
    private final Integer countOfFollows;

    @Builder
    private UserProfileInitialize(UserProfileId userProfileId, UserAccount userAccount, String nickname, ProfileImage profileImage, String introduce, Integer countOfPosts, Integer countOfFollowers, Integer countOfFollows) {
        this.userProfileId = userProfileId;
        this.userAccount = userAccount;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduce = introduce;
        this.countOfPosts = countOfPosts;
        this.countOfFollowers = countOfFollowers;
        this.countOfFollows = countOfFollows;
    }

    public static UserProfileInitialize fromEntity(UserProfileEntity entity) {
        return UserProfileInitialize.builder()
                .userProfileId(UserProfileId.of(entity.getUserProfileId()))
                .userAccount(entity.getUserAccountEntity().toModel())
                .nickname(entity.getNickname())
                .profileImage(entity.getProfileImageValue() == null ? null : entity.getProfileImageValue().to())
                .introduce(entity.getIntroduce())
                .countOfPosts(entity.getCountOfPosts())
                .countOfFollowers(entity.getCountOfFollowers())
                .countOfFollows(entity.getCountOfFollows())
                .build();
    }
}
