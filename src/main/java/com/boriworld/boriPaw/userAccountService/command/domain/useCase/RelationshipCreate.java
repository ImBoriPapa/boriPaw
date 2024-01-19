package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipType;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipSubject;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipTarget;

import java.time.LocalDateTime;

public record RelationshipCreate(
        RelationshipSubject subject,
        RelationshipTarget target,
        RelationshipType type,
        LocalDateTime relationshipAt
) {
}
