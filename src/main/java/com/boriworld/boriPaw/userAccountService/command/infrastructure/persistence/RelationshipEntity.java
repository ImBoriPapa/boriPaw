package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.model.Relationship;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.RelationshipInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "relationship")
public class RelationshipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationshipId;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserAccountEntity subject;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserAccountEntity target;
    @Enumerated(EnumType.STRING)
    private RelationshipType type;
    private LocalDateTime relationshipAt;

    @Builder(access = AccessLevel.PUBLIC)
    protected RelationshipEntity(Long relationshipId, UserAccountEntity subject, UserAccountEntity target, RelationshipType type, LocalDateTime relationshipAt) {
        this.relationshipId = relationshipId;
        this.subject = subject;
        this.target = target;
        this.type = type;
        this.relationshipAt = relationshipAt;
    }

    public static RelationshipEntity fromModel(Relationship relationship) {
        return RelationshipEntity.builder()
                .relationshipId(relationship.getRelationshipId() == null ? null : relationship.getRelationshipId().getId())
                .subject(UserAccountEntity.fromModel(relationship.getSubject().getUserAccount()))
                .target(UserAccountEntity.fromModel(relationship.getTarget().getUserAccount()))
                .type(relationship.getType())
                .relationshipAt(relationship.getRelationshipAt())
                .build();
    }

    public Relationship toModel() {
        return Relationship.initialize(RelationshipInitialize.fromEntity(this));
    }


}
