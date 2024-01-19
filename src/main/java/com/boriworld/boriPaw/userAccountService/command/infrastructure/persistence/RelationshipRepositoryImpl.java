package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.model.Relationship;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelationshipRepositoryImpl implements RelationshipRepository {

    private final RelationshipJpaRepository relationshipJpaRepository;

    @Override
    public Relationship save(Relationship relationship) {
        return relationshipJpaRepository.save(RelationshipEntity.fromModel(relationship)).toModel();
    }
}
