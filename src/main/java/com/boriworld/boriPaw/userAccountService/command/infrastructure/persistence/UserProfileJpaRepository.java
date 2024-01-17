package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileJpaRepository extends JpaRepository<UserProfileEntity,Long> {
}
