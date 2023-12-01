package com.boriworld.boriPaw.userAccountService.command.application;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AccountId;

public interface AccountManagementService {
    AccountId processUserAccountCreation(UserAccountCreate userAccountCreate);
}
