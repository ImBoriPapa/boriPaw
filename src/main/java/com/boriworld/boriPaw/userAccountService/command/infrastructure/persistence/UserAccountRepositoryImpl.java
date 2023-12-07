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
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public UserAccount save(UserAccount userAccount) {
        return accountJpaRepository.save(UserAccountEntity.fromModel(userAccount)).toModel();
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByAccountName(String accountName) {
        return accountJpaRepository.existsByAccountName(accountName);
    }

    @Override
    public Optional<UserAccount> findById(UserAccountId userAccountId) {
        return accountJpaRepository.findById(userAccountId.getId())
                .map(UserAccountEntity::toModel);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return Optional.empty();
    }
}
