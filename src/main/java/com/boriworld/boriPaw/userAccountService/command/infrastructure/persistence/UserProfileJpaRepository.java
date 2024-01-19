package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileJpaRepository extends JpaRepository<UserProfileEntity,Long> {
    Optional<UserProfileEntity> findByUserAccountEntity(UserAccountEntity userAccountEntity);
}
