package com.boriworld.boriPaw.testComponent.fakeComponents;

import com.boriworld.boriPaw.accountService.command.domain.dto.AccountInitialize;
import com.boriworld.boriPaw.accountService.command.domain.model.Account;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeAccountRepository implements AccountRepository {
    private final AtomicLong SEQUENCE = new AtomicLong(0L);
    private final Map<Long, Account> store = new ConcurrentHashMap<>();

    @Override
    public Account save(Account account) {

        long incrementAndGet = SEQUENCE.incrementAndGet();

        AccountInitialize accountInitialize = AccountInitialize.builder()
                .accountId(AccountId.of(incrementAndGet))
                .email(account.getEmail())
                .accountName(account.getAccountName())
                .password(account.getPassword())
                .nickname(account.getNickname())
                .profileImage(account.getProfileImage())
                .accountStatus(account.getAccountStatus())
                .passwordStatus(account.getPasswordStatus())
                .authority(account.getAuthority())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();

        Account initializedAccount = Account.initialize(accountInitialize);

        store.put(incrementAndGet, initializedAccount);

        return initializedAccount;
    }

    @Override
    public boolean existsByEmail(String email) {
        return store.values()
                .stream()
                .anyMatch(account -> account.getEmail().equals(email));
    }

    @Override
    public boolean existsByAccountName(String accountName) {
        return store.values()
                .stream()
                .anyMatch(account -> account.getAccountName().equals(accountName));
    }

    @Override
    public Optional<Account> findById(AccountId accountId) {
        return store.values()
                .stream()
                .filter(data -> data.getAccountId().equals(accountId))
                .findFirst();
    }
}
