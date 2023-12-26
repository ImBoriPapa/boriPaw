package com.boriworld.boriPaw.userAccountService.command.domain.service;

import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import org.springframework.security.core.Authentication;

public interface UserAuthenticationContextManager {
    void setAuthentication(UserAccountId userAccountId, Authority authority);
    Authentication getAuthentication();

}
