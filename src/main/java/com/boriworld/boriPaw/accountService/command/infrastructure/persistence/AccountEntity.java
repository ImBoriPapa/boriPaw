package com.boriworld.boriPaw.accountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.accountService.command.domain.Account;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountInitialize;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountStatus;
import com.boriworld.boriPaw.accountService.command.domain.value.Authority;
import com.boriworld.boriPaw.accountService.command.domain.value.PasswordStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String email;
    private String accountName;
    private String password;
    private String nickname;
    private String profileImage;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @Enumerated(EnumType.STRING)
    private PasswordStatus passwordStatus;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private AccountEntity(Long accountId, String email, String accountName, String password, String nickname, String profileImage, AccountStatus accountStatus, PasswordStatus passwordStatus, Authority authority, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public static AccountEntity fromDomain(Account account) {
        return AccountEntity.builder()
                .accountId(account.getAccountId() == null ? null : account.getAccountId().getId())
                .email(account.getEmail())
                .accountName(account.getAccountName())
                .password(account.getPassword())
                .nickname(account.getNickname())
                .profileImage(account.getProfileImage())
                .accountStatus(account.getAccountStatus())
                .passwordStatus(account.getPasswordStatus())
                .authority(account.getAuthority())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }

    public Account toDomain() {
        AccountInitialize initialize = AccountInitialize.builder()
                .accountId(AccountId.of(this.accountId))
                .email(this.email)
                .accountName(this.accountName)
                .password(this.password)
                .nickname(this.nickname)
                .profileImage(this.profileImage)
                .accountStatus(this.accountStatus)
                .passwordStatus(this.passwordStatus)
                .authority(this.authority)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
        return Account.initialize(initialize);
    }
}
