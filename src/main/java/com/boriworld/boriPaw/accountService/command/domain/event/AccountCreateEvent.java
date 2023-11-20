package com.boriworld.boriPaw.accountService.command.domain;

import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;

public record AccountCreateEvent(AccountId accountId, String email) {

}
