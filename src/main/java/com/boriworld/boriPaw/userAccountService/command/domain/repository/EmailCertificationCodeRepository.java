package com.boriworld.boriPaw.userAccountService.command.domain.repository;

import com.boriworld.boriPaw.userAccountService.command.domain.model.EmailCertificationCode;

public interface EmailCertificationCodeRepository {
    EmailCertificationCode save(EmailCertificationCode emailCertificationCode);
}
