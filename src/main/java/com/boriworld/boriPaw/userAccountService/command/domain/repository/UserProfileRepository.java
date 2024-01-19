package com.boriworld.boriPaw.userAccountService.command.domain.repository;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;

import java.util.Optional;

public interface UserProfileRepository {

    UserProfile save(UserProfile userProfile);
    Optional<UserProfile> findByUserAccount(UserAccount userAccount);
    void update(UserProfile updatedUserProfile);
}
