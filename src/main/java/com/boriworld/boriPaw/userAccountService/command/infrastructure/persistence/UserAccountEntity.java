package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.value.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_account")
@Entity
public class UserAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private Long userAccountId;
    private String email;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @Enumerated(EnumType.STRING)
    private PasswordStatus passwordStatus;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    @Builder(access = AccessLevel.PUBLIC)
    protected UserAccountEntity(Long userAccountId, String email, String username, String password, AccountStatus accountStatus, PasswordStatus passwordStatus, Authority authority, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastLoginAt) {
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

    public static UserAccountEntity fromModel(UserAccount userAccount) {
        return UserAccountEntity.builder()
                .userAccountId(userAccount.getUserAccountId() == null ? null : userAccount.getUserAccountId().getId())
                .email(userAccount.getEmail())
                .username(userAccount.getUsername())
                .password(userAccount.getPassword())
                .accountStatus(userAccount.getAccountStatus())
                .passwordStatus(userAccount.getPasswordStatus())
                .authority(userAccount.getAuthority())
                .createdAt(userAccount.getCreatedAt())
                .updatedAt(userAccount.getUpdatedAt())
                .lastLoginAt(userAccount.getLastLoginAt())
                .build();
    }

    public UserAccount toModel() {
        return UserAccount.initialize(UserAccountInitialize.of(this));
    }
}
