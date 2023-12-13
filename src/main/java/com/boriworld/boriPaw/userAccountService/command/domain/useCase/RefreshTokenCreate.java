package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

/**
 * UseCase
 * @param userAccountId
 * @param authority
 */
public record RefreshTokenCreate(
        UserAccountId userAccountId,
        Authority authority
        ) {


}
