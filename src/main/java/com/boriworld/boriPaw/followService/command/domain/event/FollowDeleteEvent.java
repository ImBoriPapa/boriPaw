package com.boriworld.boriPaw.followService.command.domain.event;

import com.boriworld.boriPaw.followService.command.domain.event.FollowEvent;
import com.boriworld.boriPaw.followService.command.domain.model.Follow;

public class FollowDeleteEvent implements FollowEvent {
    public FollowDeleteEvent(Follow follow) {
    }
}
