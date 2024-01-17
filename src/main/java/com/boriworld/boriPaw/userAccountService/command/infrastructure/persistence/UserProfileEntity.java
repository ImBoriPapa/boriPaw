package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserProfileInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userProfileId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userAccountId")
    private UserAccountEntity userAccountEntity;
    private String nickname;
    @Embedded
    private ProfileImageValue profileImageValue;
    private String introduce;
    private Integer countOfPosts;
    private Integer countOfFollowers;
    private Integer countOfFollows;

    @Builder(access = AccessLevel.PRIVATE)
    protected UserProfileEntity(Long userProfileId, UserAccountEntity userAccountEntity, String nickname, ProfileImageValue profileImageValue, String introduce, Integer countOfPosts, Integer countOfFollowers, Integer countOfFollows) {
        this.userProfileId = userProfileId;
        this.userAccountEntity = userAccountEntity;
        this.nickname = nickname;
        this.profileImageValue = profileImageValue;
        this.introduce = introduce;
        this.countOfPosts = countOfPosts;
        this.countOfFollowers = countOfFollowers;
        this.countOfFollows = countOfFollows;
    }

    public static UserProfileEntity form(UserProfile userProfile) {
        return UserProfileEntity.builder()
                .userProfileId(userProfile.getUserProfileId() == null ? null : userProfile.getUserProfileId().getId())
                .userAccountEntity(UserAccountEntity.fromModel(userProfile.getUserAccount()))
                .nickname(userProfile.getNickname())
                .profileImageValue(userProfile.getProfileImage() == null ? null : ProfileImageValue.form(userProfile.getProfileImage()))
                .introduce(userProfile.getIntroduce())
                .countOfPosts(userProfile.getCountOfPosts())
                .countOfFollowers(userProfile.getCountOfFollowers())
                .countOfFollows(userProfile.getCountOfFollows())
                .build();
    }

    public UserProfile toModel() {
        return UserProfile.initialize(UserProfileInitialize.fromEntity(this));
    }

}
