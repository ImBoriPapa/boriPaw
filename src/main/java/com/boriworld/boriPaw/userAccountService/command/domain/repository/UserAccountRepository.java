package com.boriworld.boriPaw.accountService.command.domain.repository;

import com.boriworld.boriPaw.accountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;

import java.util.Optional;

public interface AccountRepository {
    UserAccount save(UserAccount userAccount);
    boolean existsByEmail(String email);
    boolean existsByAccountName(String accountName);
    Optional<UserAccount> findById(AccountId accountId);
    Optional<UserAccount> findByEmail(String email);
}
