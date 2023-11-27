package com.boriworld.boriPaw.accountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.accountService.command.domain.model.Account;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;

import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account save(Account account) {
        return accountJpaRepository.save(AccountEntity.fromDomain(account)).toDomain();
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
    public Optional<Account> findById(AccountId accountId) {
        return accountJpaRepository.findById(accountId.getId())
                .map(AccountEntity::toDomain);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return Optional.empty();
    }
}
