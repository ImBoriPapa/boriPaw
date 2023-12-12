package com.boriworld.boriPaw.fakeTestComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.event.AccountEvent;
import com.boriworld.boriPaw.userAccountService.command.domain.event.UserAccountEventPublisher;

public class FakeUserAccountEventPublisher implements UserAccountEventPublisher {
    @Override
    public void publish(AccountEvent accountEvent) {
        System.out.print("Event Published");
    }
}
