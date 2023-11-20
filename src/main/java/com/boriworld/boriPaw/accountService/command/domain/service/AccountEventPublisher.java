package com.boriworld.boriPaw.accountService.command.domain;

public interface AccountEventPublisher {
    void publish(AccountCreateEvent accountCreateEvent);
}
