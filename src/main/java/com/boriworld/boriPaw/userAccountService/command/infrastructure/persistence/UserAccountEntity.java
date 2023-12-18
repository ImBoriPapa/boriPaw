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
@Entity
public class UserAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAccountId;
    private String email;
    private String userName;
    private String password;
    @Embedded
    private UserProfileValue userProfileValue;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @Enumerated(EnumType.STRING)
    private PasswordStatus passwordStatus;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    @Builder(access = AccessLevel.PRIVATE)
    protected UserAccountEntity(Long userAccountId, String email, String userName, String password, UserProfileValue userProfileValue, AccountStatus accountStatus, PasswordStatus passwordStatus, Authority authority, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastLoginAt) {
        this.userAccountId = userAccountId;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.userProfileValue = userProfileValue;
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
                .userName(userAccount.getUsername())
                .password(userAccount.getPassword())
                .userProfileValue(UserProfileValue.from(userAccount.getUserProfile()))
                .accountStatus(userAccount.getAccountStatus())
                .passwordStatus(userAccount.getPasswordStatus())
                .authority(userAccount.getAuthority())
                .createdAt(userAccount.getCreatedAt())
                .updatedAt(userAccount.getUpdatedAt())
                .lastLoginAt(userAccount.getLastLoginAt())
                .build();
    }

    public UserAccount toModel() {
        UserAccountInitialize initialize = UserAccountInitialize.builder()
                .userAccountId(UserAccountId.of(this.userAccountId))
                .email(this.email)
                .userName(this.userName)
                .password(this.password)
                .userProfile(this.userProfileValue.to())
                .accountStatus(this.accountStatus)
                .passwordStatus(this.passwordStatus)
                .authority(this.authority)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .lastLoginAt(this.lastLoginAt)
                .build();
        return UserAccount.initialize(initialize);
    }
}
