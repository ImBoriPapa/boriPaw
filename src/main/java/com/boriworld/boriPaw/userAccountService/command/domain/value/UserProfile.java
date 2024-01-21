package com.boriworld.boriPaw.userAccountService.command.domain.value;


import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.ProfileImageUpdate;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserProfileCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserProfileInitialize;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;


@Getter
public final class UserProfile {
    private final UserProfileId userProfileId;
    private final UserAccount userAccount;
    private final String nickname;
    private final ProfileImage profileImage;
    private final String introduce;
    private final Integer countOfPosts;
    private final Integer countOfFollowers;
    private final Integer countOfFollows;

    @Builder(access = AccessLevel.PRIVATE)
    private UserProfile(UserProfileId userProfileId, UserAccount userAccount, String nickname, ProfileImage profileImage, String introduce, Integer countOfPosts, Integer countOfFollowers, Integer countOfFollows) {
        this.userProfileId = userProfileId;
        this.userAccount = userAccount;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduce = introduce;
        this.countOfPosts = countOfPosts;
        this.countOfFollowers = countOfFollowers;
        this.countOfFollows = countOfFollows;
    }

    public static UserProfile of(UserProfileCreate userProfileCreate) {
        final String introduce = "자기 소개를 입력해주세요";
        return UserProfile.builder()
                .userAccount(userProfileCreate.userAccount())
                .nickname(userProfileCreate.nickname())
                .profileImage(null)
                .introduce(introduce)
                .countOfPosts(0)
                .countOfFollowers(0)
                .countOfFollows(0)
                .build();
    }

    public static UserProfile initialize(UserProfileInitialize initialize) {
        return UserProfile.builder()
                .userProfileId(initialize.getUserProfileId())
                .userAccount(initialize.getUserAccount())
                .nickname(initialize.getNickname())
                .profileImage(initialize.getProfileImage())
                .introduce(initialize.getIntroduce())
                .countOfPosts(initialize.getCountOfPosts())
                .countOfFollowers(initialize.getCountOfFollowers())
                .countOfFollows(initialize.getCountOfFollows())
                .build();
    }

    public UserProfile updateNickname(String newNickname) {

        if (!StringUtils.hasText(newNickname)) {
            throw new RuntimeException("new nickname is null");
        }

        return UserProfile.builder()
                .userProfileId(this.userProfileId)
                .userAccount(this.userAccount.changeUpdatedAt())
                .nickname(newNickname)
                .profileImage(this.profileImage)
                .introduce(this.introduce)
                .countOfPosts(this.countOfPosts)
                .countOfFollowers(this.countOfFollowers)
                .countOfFollows(this.countOfFollows)
                .build();
    }

    public UserProfile updateIntroduce(String newIntroduce) {
        if (!StringUtils.hasText(newIntroduce)) {
            throw new RuntimeException("new nickname is null");
        }

        return UserProfile.builder()
                .userProfileId(this.userProfileId)
                .userAccount(this.userAccount.changeUpdatedAt())
                .nickname(this.nickname)
                .profileImage(this.profileImage)
                .introduce(newIntroduce)
                .countOfPosts(this.countOfPosts)
                .countOfFollowers(this.countOfFollowers)
                .countOfFollows(this.countOfFollows)
                .build();
    }

    public UserProfile updateProfileImage(ProfileImage profileImage) {

        return UserProfile.builder()
                .userProfileId(this.userProfileId)
                .userAccount(this.userAccount.changeUpdatedAt())
                .nickname(this.nickname)
                .profileImage(profileImage)
                .introduce(this.introduce)
                .countOfPosts(this.countOfPosts)
                .countOfFollowers(this.countOfFollowers)
                .countOfFollows(this.countOfFollows)
                .build();
    }
}
