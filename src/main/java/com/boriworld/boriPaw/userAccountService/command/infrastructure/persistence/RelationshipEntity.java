package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.model.RelationshipType;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RelationshipEntity {
    private Long relationshipId;
    private Long subject;
    private Long target;
    private RelationshipType type;
    private LocalDateTime relationshipAt;

    public RelationshipEntity(Long relationshipId, Long subject, Long target, RelationshipType type, LocalDateTime relationshipAt) {
        this.relationshipId = relationshipId;
        this.subject = subject;
        this.target = target;
        this.type = type;
        this.relationshipAt = relationshipAt;
    }
}
