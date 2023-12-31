package com.boriworld.boriPaw.followService.command.domain.event;

import com.boriworld.boriPaw.followService.command.domain.event.FollowEvent;

public interface FollowEventPublisher {
    void publish(FollowEvent followEvent);
}
