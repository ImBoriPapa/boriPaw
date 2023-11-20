package com.boriworld.boriPaw.testContanier;

import com.boriworld.boriPaw.accountService.command.domain.event.AccountCreateEvent;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountEventPublisher;

public class FakeAccountEventPublisher implements AccountEventPublisher {
    @Override
    public void publish(AccountCreateEvent accountCreateEvent) {
        System.out.print("Event Published");
    }
}
