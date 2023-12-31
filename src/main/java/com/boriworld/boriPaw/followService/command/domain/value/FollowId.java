package com.boriworld.boriPaw.followService.command.domain.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public final class FollowId {
    private final Long id;
    private FollowId(Long id) {
        this.id = id;
    }

    public static FollowId of(Long id) {
        return new FollowId(id);
    }
}
