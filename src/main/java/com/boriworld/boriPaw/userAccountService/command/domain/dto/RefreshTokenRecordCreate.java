package com.boriworld.boriPaw.userAccountService.command.domain.dto;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

public record RefreshTokenRecordCreate(String refreshToken, UserAccountId userAccountId, long timeToLive) {
}
