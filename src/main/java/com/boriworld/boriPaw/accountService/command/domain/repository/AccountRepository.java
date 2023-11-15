package com.boriworld.boriPaw.accountService.command.domain.repository;

import com.boriworld.boriPaw.accountService.command.domain.Account;

public interface AccountRepository {
    Account save(Account account);
    boolean existsByEmail(String email);
    boolean existsByAccountName(String accountName);
}
