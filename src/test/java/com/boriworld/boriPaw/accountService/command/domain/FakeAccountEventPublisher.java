package com.boriworld.boriPaw.accountService.command.domain;

import com.boriworld.boriPaw.accountService.command.domain.AccountCreateEvent;
import com.boriworld.boriPaw.accountService.command.domain.AccountEventPublisher;

public class FakeAccountEventPublisher implements AccountEventPublisher {
    @Override
    public void publish(AccountCreateEvent accountCreateEvent) {
        System.out.print("Event Published");
    }
}
