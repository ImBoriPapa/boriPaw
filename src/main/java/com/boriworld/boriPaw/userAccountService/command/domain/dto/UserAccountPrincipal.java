package com.boriworld.boriPaw.userAccountService.command.domain.dto;

import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

public record UserAccountPrincipal(UserAccountId userAccountId, Authority authority) {
}
