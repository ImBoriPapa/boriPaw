package com.boriworld.boriPaw.userAccountService.query.domain.repository;

import com.boriworld.boriPaw.followService.query.domain.value.Requester;
import com.boriworld.boriPaw.userAccountService.command.domain.model.Relationship;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.domain.model.RelationshipInformation;

import java.util.Optional;

public interface RelationshipQueryRepository {
    Optional<RelationshipInformation> findByRequesterAndTarget(Requester requester, UserAccountId userAccountId);
}
