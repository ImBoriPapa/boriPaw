package com.boriworld.boriPaw.userAccountService.command.domain.dto;

import com.boriworld.boriPaw.userAccountService.command.domain.value.*;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record AccountInitialize(
        AccountId accountId,
        String email,
        String accountName,
        String password,
        String nickname,
        String profileImage,
        AccountStatus accountStatus,
        PasswordStatus passwordStatus,
        Authority authority,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime lastLoginAt) {

}
