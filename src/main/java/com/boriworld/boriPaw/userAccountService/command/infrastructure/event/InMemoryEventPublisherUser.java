package com.boriworld.boriPaw.userAccountService.command.infrastructure.event;

import com.boriworld.boriPaw.userAccountService.command.domain.event.AccountEvent;
import com.boriworld.boriPaw.userAccountService.command.domain.event.UserAccountEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InMemoryEventPublisherUser implements UserAccountEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(AccountEvent accountEvent) {
        applicationEventPublisher.publishEvent(accountEvent);
    }
}
