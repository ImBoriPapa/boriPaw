package com.boriworld.boriPaw.userAccountService.command.domain.value;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public final class UserAccountId {
    private final Long id;

    private UserAccountId(Long id) {
        this.id = id;
    }

    public static UserAccountId of(long id) {
        return new UserAccountId(id);
    }

    public Long getId() {
        return this.id;
    }
}
