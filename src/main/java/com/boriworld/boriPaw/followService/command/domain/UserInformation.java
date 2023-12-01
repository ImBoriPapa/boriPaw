package com.boriworld.boriPaw.followService.command.domain;

import java.util.Set;

public final class FollowedUser {
    private final FollowUserId followUserId;
    private final AccountUserId accountUserId;
    private final String nickname;
    private final String profileImage;
    private final Set<AccountUserId> blockedAccounts;

    private FollowedUser(FollowUserId followUserId, AccountUserId accountUserId, String nickname, String profileImage, Set<AccountUserId> accountUserIds) {
        this.followUserId = followUserId;
        this.accountUserId = accountUserId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.blockedAccounts = Set.copyOf(accountUserIds);
    }

    public static FollowedUser of(FollowUserCreate create) {
        return new FollowedUser(
                null,
                new AccountUserId(create.accountId()),
                create.nickname(),
                create.profileImage(),
                Set.of()
        );
    }

}
