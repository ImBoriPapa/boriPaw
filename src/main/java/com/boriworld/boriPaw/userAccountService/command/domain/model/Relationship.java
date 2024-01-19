package com.boriworld.boriPaw.userAccountService.command.domain.model;

import com.boriworld.boriPaw.userAccountService.command.domain.useCase.RelationshipCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.RelationshipInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipId;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipSubject;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipTarget;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class Relationship {
    private final RelationshipId relationshipId;
    private final RelationshipSubject subject;
    private final RelationshipTarget target;
    private final RelationshipType type;
    private final LocalDateTime relationshipAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Relationship(RelationshipId relationshipId, RelationshipSubject subject, RelationshipTarget target, RelationshipType type, LocalDateTime relationshipAt) {
        this.relationshipId = relationshipId;
        this.subject = subject;
        this.target = target;
        this.type = type;
        this.relationshipAt = relationshipAt;
    }

    public static Relationship of(RelationshipCreate create) {
        return Relationship.builder()
                .subject(create.subject())
                .target(create.target())
                .type(create.type())
                .relationshipAt(create.relationshipAt())
                .build();
    }

    /**
     * (warn)Data Access Layer or Infrastructure 레이어에서 Model 로 맴핑하기 위한 메서드 입니다. 다른 목적으로 사용하지 마십시오.
     */
    public static Relationship initialize(RelationshipInitialize initialize) {
        return Relationship.builder()
                .relationshipId(initialize.getRelationshipId())
                .subject(initialize.getSubject())
                .target(initialize.getTarget())
                .type(initialize.getType())
                .relationshipAt(initialize.getRelationshipAt())
                .build();
    }
}
