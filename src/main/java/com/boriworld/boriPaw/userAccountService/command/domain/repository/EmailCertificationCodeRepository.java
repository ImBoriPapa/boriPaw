package com.boriworld.boriPaw.accountService.command.domain.repository;

import com.boriworld.boriPaw.accountService.command.domain.model.EmailCertificationCode;

public interface EmailCertificationCodeRepository {
    EmailCertificationCode save(EmailCertificationCode emailCertificationCode);
}
