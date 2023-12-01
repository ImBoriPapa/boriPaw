package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<UserAccountEntity,Long> {
    boolean existsByEmail(String email);
    boolean existsByAccountName(String accountName);
}
