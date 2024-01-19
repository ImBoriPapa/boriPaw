package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountJpaRepository extends JpaRepository<UserAccountEntity,Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String accountName);
    Optional<UserAccountEntity> findByEmail(String email);


}
