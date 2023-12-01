package com.boriworld.boriPaw.testComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AccountInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AccountId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserAccountRepository implements UserAccountRepository {
    private final AtomicLong SEQUENCE = new AtomicLong(0L);
    private final Map<Long, UserAccount> store = new ConcurrentHashMap<>();

    @Override
    public UserAccount save(UserAccount userAccount) {

        long incrementAndGet = SEQUENCE.incrementAndGet();

        AccountInitialize accountInitialize = AccountInitialize.builder()
                .accountId(AccountId.of(incrementAndGet))
                .email(userAccount.getEmail())
                .accountName(userAccount.getAccountName())
                .password(userAccount.getPassword())
                .nickname(userAccount.getNickname())
                .profileImage(userAccount.getProfileImage())
                .accountStatus(userAccount.getAccountStatus())
                .passwordStatus(userAccount.getPasswordStatus())
                .authority(userAccount.getAuthority())
                .createdAt(userAccount.getCreatedAt())
                .updatedAt(userAccount.getUpdatedAt())
                .build();

        UserAccount initializedUserAccount = UserAccount.initialize(accountInitialize);

        store.put(incrementAndGet, initializedUserAccount);

        return initializedUserAccount;
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
    public Optional<UserAccount> findById(AccountId accountId) {
        return store.values()
                .stream()
                .filter(data -> data.getAccountId().equals(accountId))
                .findFirst();
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return Optional.empty();
    }
}
