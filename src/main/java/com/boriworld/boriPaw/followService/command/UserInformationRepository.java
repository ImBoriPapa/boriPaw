package com.boriworld.boriPaw.followService.command;

import com.boriworld.boriPaw.followService.command.domain.AccountUserId;
import com.boriworld.boriPaw.followService.command.domain.UserInformation;

import java.util.Optional;

public interface UserInformationRepository {
    Optional<UserInformation> findByAccountUserId(AccountUserId accountUserId);

    UserInformation save(UserInformation userInformation);
}
