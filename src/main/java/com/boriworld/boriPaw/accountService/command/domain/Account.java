package com.boriworld.boriPaw.accountService.command.domain;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

public final class Account {
    private final AccountId accountId;
    private final String email;
    private final String accountName;
    private final String password;
    private final String nickname;
    private final String profileImage;
    private final AccountStatus accountStatus;
    private final PasswordStatus passwordStatus;
    private final Authority authority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Account(AccountId accountId, String email, String accountName, String password, String nickname, String profileImage, AccountStatus accountStatus, PasswordStatus passwordStatus, Authority authority, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.accountId = accountId;
        this.email = email;
        this.accountName = accountName;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.accountStatus = accountStatus;
        this.passwordStatus = passwordStatus;
        this.authority = authority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Account createAccount() {
        return Account.builder()
                .build();
    }

    public AccountId getAccountId() {
        return this.accountId;
    }
}
