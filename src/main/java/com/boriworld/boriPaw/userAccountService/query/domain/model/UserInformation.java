package com.boriworld.boriPaw.userAccountService.query.domain.model;

import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public final class UserInformation {
    private final Long userAccountId;
    private final Authority authority;
    private final LocalDateTime lastLoginAt;

    public UserInformation(Long userAccountId, Authority authority, LocalDateTime lastLoginAt) {
        this.userAccountId = userAccountId;
        this.authority = authority;
        this.lastLoginAt = lastLoginAt;
    }
}
