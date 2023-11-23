package com.boriworld.boriPaw.testComponent.fakeComponents;

import com.boriworld.boriPaw.accountService.command.domain.event.AccountEvent;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountEventPublisher;

public class FakeAccountEventPublisher implements AccountEventPublisher {
    @Override
    public void publish(AccountEvent accountEvent) {
        System.out.print("Event Published");
    }
}
