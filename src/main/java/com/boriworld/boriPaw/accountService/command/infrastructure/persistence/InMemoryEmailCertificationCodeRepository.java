package com.boriworld.boriPaw.accountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.accountService.command.domain.repository.EmailAuthenticationCodeRepository;
import com.boriworld.boriPaw.accountService.command.domain.model.EmailAuthenticationCode;
import org.springframework.stereotype.Component;

@Component
public class InMemoryEmailAuthenticationCodeRepository implements EmailAuthenticationCodeRepository {
    @Override
    public EmailAuthenticationCode save(EmailAuthenticationCode emailAuthenticationCode) {
        return null;
    }
}
