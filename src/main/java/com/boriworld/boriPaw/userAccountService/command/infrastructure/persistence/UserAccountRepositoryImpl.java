package com.boriworld.boriPaw.accountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.accountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;

import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public UserAccount save(UserAccount userAccount) {
        return accountJpaRepository.save(AccountEntity.fromDomain(userAccount)).toDomain();
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
    public Optional<UserAccount> findById(AccountId accountId) {
        return accountJpaRepository.findById(accountId.getId())
                .map(AccountEntity::toDomain);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return Optional.empty();
    }
}
