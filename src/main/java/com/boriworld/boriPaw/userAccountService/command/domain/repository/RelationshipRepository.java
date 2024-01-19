package com.boriworld.boriPaw.userAccountService.command.domain.repository;

import com.boriworld.boriPaw.userAccountService.command.domain.model.Relationship;

public interface RelationshipRepository {
    Relationship save(Relationship relationship);
}
