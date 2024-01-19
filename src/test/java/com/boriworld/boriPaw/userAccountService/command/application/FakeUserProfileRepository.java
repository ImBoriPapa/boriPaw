package com.boriworld.boriPaw.userAccountService.command.application;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserProfileRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;

import java.util.Optional;

public class FakeUserProfileRepository implements UserProfileRepository {
    @Override
    public UserProfile save(UserProfile userProfile) {
        return null;
    }

    @Override
    public Optional<UserProfile> findByUserAccount(UserAccount userAccount) {
        return Optional.empty();
    }

    @Override
    public void update(UserProfile updatedUserProfile) {

    }
}
