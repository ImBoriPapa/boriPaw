package com.boriworld.boriPaw.followService.command.domain.event;

public interface FollowEventPublisher {
    void publish(FollowEvent followEvent);
}
