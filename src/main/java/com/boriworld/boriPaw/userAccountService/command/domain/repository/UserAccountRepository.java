package com.boriworld.boriPaw.userAccountService.command.domain.repository;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

import java.util.Optional;

public interface UserAccountRepository {
    UserAccount save(UserAccount userAccount);
    boolean existsByEmail(String email);
    boolean existsByUsername(String accountName);
    Optional<UserAccount> findById(UserAccountId userAccountId);
    Optional<UserAccount> findByEmail(String email);
    UserAccount update(UserAccount userAccount);
}
