package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserAccountRepositoryImpl implements UserAccountRepository {
    private final UserAccountJpaRepository userAccountJpaRepository;

    @Override
    public UserAccount save(UserAccount userAccount) {
        return userAccountJpaRepository.save(UserAccountEntity.fromModel(userAccount)).toModel();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userAccountJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String accountName) {
        return userAccountJpaRepository.existsByUserName(accountName);
    }

    @Override
    public Optional<UserAccount> findById(UserAccountId userAccountId) {
        return userAccountJpaRepository.findById(userAccountId.getId())
                .map(UserAccountEntity::toModel);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return userAccountJpaRepository.findByEmail(email).map(UserAccountEntity::toModel);
    }

    @Override
    public UserAccount update(UserAccount userAccount) {
        return userAccountJpaRepository.save(UserAccountEntity.fromModel(userAccount)).toModel();
    }
}
