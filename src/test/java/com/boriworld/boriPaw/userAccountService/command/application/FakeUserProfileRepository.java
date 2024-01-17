package com.boriworld.boriPaw.userAccountService.command.application;

import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserProfileRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;

public class FakeUserProfileRepository implements UserProfileRepository {
    @Override
    public UserProfile save(UserProfile userProfile) {
        return null;
    }
}
