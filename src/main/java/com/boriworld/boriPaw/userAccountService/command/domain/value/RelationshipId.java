package com.boriworld.boriPaw.userAccountService.command.domain.value;

import lombok.Getter;

@Getter
public final class RelationshipId {
    private final Long id;

    private RelationshipId(Long id) {
        this.id = id;
    }

    public static RelationshipId of(Long id) {
        return new RelationshipId(id);
    }
}
