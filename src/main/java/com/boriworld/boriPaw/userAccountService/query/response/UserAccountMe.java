package com.boriworld.boriPaw.userAccountService.query.response;

import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;

import java.time.LocalDateTime;

public record UserAccountMe(Long userAccountId, Authority authority, LocalDateTime lastLoginAt) {
}
