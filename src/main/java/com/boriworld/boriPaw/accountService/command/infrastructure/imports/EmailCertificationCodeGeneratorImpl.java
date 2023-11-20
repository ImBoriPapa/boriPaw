package com.boriworld.boriPaw.accountService.command.infrastructure.imports;

import com.boriworld.boriPaw.accountService.command.domain.service.EmailAuthenticationCodeGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailAuthenticationCodeGeneratorImpl implements EmailAuthenticationCodeGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
