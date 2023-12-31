package com.boriworld.boriPaw.userAccountService.query.domain.repository;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserInformation;

import java.util.Optional;

public interface UserAuthenticationQueryRepository {
    Optional<UserInformation> findUserInformationByUserAccountId(UserAccountId id);
}
