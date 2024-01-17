package com.boriworld.boriPaw.userAccountService.command.domain.model;

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
                .build();
    }
}
