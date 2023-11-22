package com.boriworld.boriPaw.accountService.command.infrastructure.event;

import com.boriworld.boriPaw.accountService.command.domain.event.AccountCreateEvent;
import com.boriworld.boriPaw.accountService.command.domain.event.AccountEvent;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InMemoryEventPublisher implements AccountEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(AccountEvent accountEvent) {
        applicationEventPublisher.publishEvent(accountEvent);
    }
}
