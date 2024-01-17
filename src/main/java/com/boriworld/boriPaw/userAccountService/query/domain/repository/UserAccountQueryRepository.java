package com.boriworld.boriPaw.userAccountService.query.domain.repository;

import com.boriworld.boriPaw.followService.query.domain.value.Requester;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserInformation;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserProfileDetail;

import java.util.Optional;

public interface UserAccountQueryRepository {
    Optional<UserProfileDetail> findUserProfileDetailByUserAccountId(UserAccountId userAccountId);
}
