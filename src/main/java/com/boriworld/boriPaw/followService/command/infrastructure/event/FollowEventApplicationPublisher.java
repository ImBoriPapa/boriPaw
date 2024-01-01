package com.boriworld.boriPaw.followService.command.infrastructure.event;

import com.boriworld.boriPaw.followService.command.domain.event.FollowEvent;
import com.boriworld.boriPaw.followService.command.domain.event.FollowEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowEventApplicationPublisher implements FollowEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(FollowEvent followEvent) {
        publisher.publishEvent(followEvent);
    }
}
