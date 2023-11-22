package com.boriworld.boriPaw.testContanier;

import com.boriworld.boriPaw.accountService.command.domain.event.AccountCreateEvent;
import com.boriworld.boriPaw.accountService.command.domain.event.AccountEvent;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountEventPublisher;

public class FakeAccountEventPublisher implements AccountEventPublisher {
    @Override
    public void publish(AccountEvent accountEvent) {
        System.out.print("Event Published");
    }
}
