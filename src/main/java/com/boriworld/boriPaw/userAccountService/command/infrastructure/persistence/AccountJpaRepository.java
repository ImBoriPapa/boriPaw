package com.boriworld.boriPaw.accountService.command.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<AccountEntity,Long> {
    boolean existsByEmail(String email);
    boolean existsByAccountName(String accountName);
}
