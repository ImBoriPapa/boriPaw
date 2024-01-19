package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipId;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipSubject;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipTarget;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipType;
import com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.RelationshipEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class RelationshipInitialize {

    private final RelationshipId relationshipId;
    private final RelationshipSubject subject;
    private final RelationshipTarget target;
    private final RelationshipType type;
    private final LocalDateTime relationshipAt;

    @Builder(access = AccessLevel.PRIVATE)
    private RelationshipInitialize(RelationshipId relationshipId, RelationshipSubject subject, RelationshipTarget target, RelationshipType type, LocalDateTime relationshipAt) {
        this.relationshipId = relationshipId;
        this.subject = subject;
        this.target = target;
        this.type = type;
        this.relationshipAt = relationshipAt;
    }

    public static RelationshipInitialize fromEntity(RelationshipEntity entity) {
        return RelationshipInitialize.builder()
                .relationshipId(RelationshipId.of(entity.getRelationshipId()))
                .subject(RelationshipSubject.of(entity.getSubject().toModel()))
                .target(RelationshipTarget.of(entity.getTarget().toModel()))
                .type(entity.getType())
                .relationshipAt(entity.getRelationshipAt())
                .build();
    }
}
