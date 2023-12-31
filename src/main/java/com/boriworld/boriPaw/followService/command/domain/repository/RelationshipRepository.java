package com.boriworld.boriPaw.followService.command.domain.repository;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

public interface RelationshipRepository {
    boolean existsByUserAccountId(UserAccountId userAccountId);
}
