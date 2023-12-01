package com.boriworld.boriPaw.accountService.command.domain.service;

import com.boriworld.boriPaw.accountService.command.domain.event.AccountEvent;

public interface AccountEventPublisher {
    void publish(AccountEvent accountEvent);
}
