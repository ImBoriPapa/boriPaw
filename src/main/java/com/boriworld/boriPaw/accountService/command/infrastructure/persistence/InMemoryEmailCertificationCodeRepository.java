package com.boriworld.boriPaw.accountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.accountService.command.domain.repository.EmailCertificationCodeRepository;
import com.boriworld.boriPaw.accountService.command.domain.model.EmailCertificationCode;
import org.springframework.stereotype.Component;

@Component
public class InMemoryEmailCertificationCodeRepository implements EmailCertificationCodeRepository {
    @Override
    public EmailCertificationCode save(EmailCertificationCode emailCertificationCode) {
        return null;
    }
}
