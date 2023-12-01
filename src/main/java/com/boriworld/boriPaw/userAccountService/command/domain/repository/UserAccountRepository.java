package com.boriworld.boriPaw.userAccountService.command.domain.repository;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AccountId;

import java.util.Optional;

public interface UserAccountRepository {
    UserAccount save(UserAccount userAccount);
    boolean existsByEmail(String email);
    boolean existsByAccountName(String accountName);
    Optional<UserAccount> findById(AccountId accountId);
    Optional<UserAccount> findByEmail(String email);
}
