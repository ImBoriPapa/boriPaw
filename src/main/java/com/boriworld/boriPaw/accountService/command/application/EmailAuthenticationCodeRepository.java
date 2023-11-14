package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.EmailAuthenticationCode;

public interface EmailAuthenticationCodeRepository {
    EmailAuthenticationCode save(EmailAuthenticationCode emailAuthenticationCode);
}
