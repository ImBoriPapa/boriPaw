package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

import com.boriworld.boriPaw.userAccountService.command.domain.value.*;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record UserAccountInitialize(
        UserAccountId userAccountId,
        String email,
        String userName,
        String password,
        UserProfile userProfile,
        AccountStatus accountStatus,
        PasswordStatus passwordStatus,
        Authority authority,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime lastLoginAt) {

}
