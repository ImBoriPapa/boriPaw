package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<UserAccountEntity,Long> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String accountName);

    Optional<UserAccountEntity> findByEmail(String email);
}
