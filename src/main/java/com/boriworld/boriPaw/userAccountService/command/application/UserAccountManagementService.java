package com.boriworld.boriPaw.userAccountService.command.application;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

public interface UserAccountManagementService {
    UserAccountId processUserAccountCreation(UserAccountCreate userAccountCreate);
}
