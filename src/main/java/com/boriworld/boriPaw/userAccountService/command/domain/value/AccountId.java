package com.boriworld.boriPaw.userAccountService.command.domain.value;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public final class AccountId {
    private final Long id;

    private AccountId(Long id) {
        this.id = id;
    }

    public static AccountId of(long id) {
        return new AccountId(id);
    }

    public Long getId() {
        return this.id;
    }
}
