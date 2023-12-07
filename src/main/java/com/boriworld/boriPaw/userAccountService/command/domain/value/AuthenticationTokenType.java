package com.boriworld.boriPaw.userAccountService.command.domain.value;

import lombok.Getter;

import java.util.concurrent.TimeUnit;
@Getter
/**
 * Value Object
 */

public enum AuthenticationTokenType {

    ACCESS_TOKEN(TimeUnit.MINUTES),
    REFRESH_TOKEN(TimeUnit.DAYS);
    private final TimeUnit expirationTimeUnit;
    AuthenticationTokenType(TimeUnit timeUnit) {
        this.expirationTimeUnit = timeUnit;
    }

}
