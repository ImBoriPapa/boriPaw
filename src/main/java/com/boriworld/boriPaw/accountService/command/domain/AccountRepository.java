package com.boriworld.boriPaw.accountService.command.domain;

public interface AccountRepository {
    Account save(Account account);
    boolean existsByEmail(String email);
}
