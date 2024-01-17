package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;

public record UserProfileCreate(UserAccount userAccount, String nickname) {
}
