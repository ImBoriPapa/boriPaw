package com.boriworld.boriPaw.userAccountService.query.domain.model;

import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class UserInformation {
    private final Long userAccountId;
    private final Authority authority;
    private final String username;
    private final String profileImage;
    private final LocalDateTime lastLoginAt;

    public UserInformation(Long userAccountId, Authority authority,String username, String profileImage, LocalDateTime lastLoginAt) {
        this.userAccountId = userAccountId;
        this.authority = authority;
        this.username = username;
        this.profileImage = profileImage;
        this.lastLoginAt = lastLoginAt;
    }
}
