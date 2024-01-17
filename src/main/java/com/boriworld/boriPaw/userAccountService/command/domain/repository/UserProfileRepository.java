package com.boriworld.boriPaw.userAccountService.command.domain.repository;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;

public interface UserProfileRepository {

    UserProfile save(UserProfile userProfile);
}
