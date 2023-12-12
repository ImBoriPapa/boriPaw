package com.boriworld.boriPaw.fakeTestComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;

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
        UserProfile userProfile = UserProfile.of(userAccount.getUserProfile().getNickname(), userAccount.getUserProfile().getProfileImage());
        UserAccountInitialize accountInitialize = UserAccountInitialize.builder()
                .userAccountId(UserAccountId.of(incrementAndGet))
                .email(userAccount.getEmail())
                .accountName(userAccount.getAccountName())
                .password(userAccount.getPassword())
                .userProfile(userProfile)
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
    public Optional<UserAccount> findById(UserAccountId userAccountId) {
        return store.values()
                .stream()
                .filter(data -> data.getUserAccountId().equals(userAccountId))
                .findFirst();
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return Optional.empty();
    }
}
