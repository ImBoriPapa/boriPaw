package com.boriworld.boriPaw.accountService.command.domain.repository;

import com.boriworld.boriPaw.accountService.command.domain.model.Account;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountStatus;

import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);
    boolean existsByEmail(String email);
    boolean existsByAccountName(String accountName);
    Optional<Account> findById(AccountId accountId);
    Optional<Account> findByEmail(String email);
}
