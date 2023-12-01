package com.boriworld.boriPaw.userAccountService.command.domain.service;

import com.boriworld.boriPaw.userAccountService.command.domain.event.AccountEvent;

public interface UserAccountEventPublisher {
    void publish(AccountEvent accountEvent);
}
