package com.boriworld.boriPaw.accountService.command.domain.value;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public final class AccountId {
    private final Long id;

    private AccountId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}
