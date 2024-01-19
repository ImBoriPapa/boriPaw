package com.boriworld.boriPaw.fakeTestComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.UserAccountEntity;


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

        UserAccountEntity accountEntity = UserAccountEntity.builder()
                .userAccountId(incrementAndGet)
                .email(userAccount.getEmail())
                .username(userAccount.getUsername())
                .password(userAccount.getPassword())
                .accountStatus(userAccount.getAccountStatus())
                .passwordStatus(userAccount.getPasswordStatus())
                .authority(userAccount.getAuthority())
                .createdAt(userAccount.getCreatedAt())
                .updatedAt(userAccount.getUpdatedAt())
                .build();


        UserAccount initializedUserAccount = UserAccount.initialize(UserAccountInitialize.of(accountEntity));

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
    public boolean existsByUsername(String username) {
        return store.values()
                .stream()
                .anyMatch(account -> account.getUsername().equals(username));
    }

    @Override
    public Optional<UserAccount> findById(UserAccountId userAccountId) {
        return store.values()
                .stream()
                .filter(data -> data.getUserAccountId().equals(userAccountId))
                .findFirst();
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return store.values()
                .stream()
                .filter(data -> data.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public UserAccount update(UserAccount userAccount) {
        store.replace(userAccount.getUserAccountId().getId(), userAccount);
        return store.get(userAccount.getUserAccountId().getId());
    }
}
