package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;

public interface AccountManagementService {
    AccountId processAccountCreation(AccountCreate accountCreate);
}
