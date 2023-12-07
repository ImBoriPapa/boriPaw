package com.boriworld.boriPaw.userAccountService.command.domain.model;

import com.boriworld.boriPaw.userAccountService.command.domain.value.AccountStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

public record AuthenticationTokenCreate(
        UserAccountId userAccountId,
        Authority authority,
        AccountStatus accountStatus
) {
}
