package com.boriworld.boriPaw.userAccountService.command.domain.service;

import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

public interface SecurityContextManager {
    void setAuthentication(UserAccountId userAccountId, Authority authority);

}
