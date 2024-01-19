package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

import com.boriworld.boriPaw.userAccountService.command.domain.value.*;
import com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.UserAccountEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class UserAccountInitialize {
    private final UserAccountId userAccountId;
    private final String email;
    private final String username;
    private final String password;
    private final AccountStatus accountStatus;
    private final PasswordStatus passwordStatus;
    private final Authority authority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime lastLoginAt;

    @Builder(access = AccessLevel.PRIVATE)
    private UserAccountInitialize(UserAccountId userAccountId, String email, String username, String password, AccountStatus accountStatus, PasswordStatus passwordStatus, Authority authority, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastLoginAt) {
        this.userAccountId = userAccountId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.accountStatus = accountStatus;
        this.passwordStatus = passwordStatus;
        this.authority = authority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLoginAt = lastLoginAt;
    }

    public static UserAccountInitialize of(UserAccountEntity entity) {
        return UserAccountInitialize.builder()
                .userAccountId(UserAccountId.of(entity.getUserAccountId()))
                .email(entity.getEmail())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .accountStatus(entity.getAccountStatus())
                .passwordStatus(entity.getPasswordStatus())
                .authority(entity.getAuthority())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .lastLoginAt(entity.getLastLoginAt())
                .build();
    }

}
