package com.boriworld.boriPaw.accountService.command.infrastructure.event;

import com.boriworld.boriPaw.accountService.command.domain.AccountCreateEvent;
import com.boriworld.boriPaw.accountService.command.domain.AccountEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InMemoryEventPublisher implements AccountEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(AccountCreateEvent accountCreateEvent) {
        applicationEventPublisher.publishEvent(accountCreateEvent);
    }
}