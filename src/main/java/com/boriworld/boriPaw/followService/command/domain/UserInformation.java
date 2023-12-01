package com.boriworld.boriPaw.followService.command.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
public final class UserInformation {
    private final UserInformationId userInformationId;
    private final AccountUserId accountUserId;
    private final String nickname;
    private final String profileImage;
    private final Set<AccountUserId> blockedAccounts;

    @Builder
    private UserInformation(UserInformationId userInformationId, AccountUserId accountUserId, String nickname, String profileImage, Set<AccountUserId> blockedAccountIds) {
        this.userInformationId = userInformationId;
        this.accountUserId = accountUserId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.blockedAccounts = blockedAccountIds;
    }

    public static UserInformation of(FollowUserCreate create) {
        return UserInformation.builder()
                .accountUserId(new AccountUserId(create.accountId()))
                .nickname(create.nickname())
                .profileImage(create.profileImage())
                .blockedAccountIds(Set.of())
                .build();
    }

    public void checkBlockedUser(AccountUserId accountUserId) {
        if (this.blockedAccounts.contains(accountUserId)) {
            throw new RuntimeException("팔로우 할 수 없음");
        }
    }

}
