package com.boriworld.boriPaw.userAccountService.command.domain.model;

import java.time.LocalDateTime;

public record RelationshipCreate(
        RelationshipSubject subject, RelationshipTarget target, RelationshipType type, LocalDateTime relationshipAt
) {
}
