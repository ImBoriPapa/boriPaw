package com.boriworld.boriPaw.accountService.command.domain.repository;

import com.boriworld.boriPaw.accountService.command.domain.model.EmailAuthenticationCode;

public interface EmailAuthenticationCodeRepository {
    EmailAuthenticationCode save(EmailAuthenticationCode emailAuthenticationCode);
}
