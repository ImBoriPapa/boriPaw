package com.boriworld.boriPaw.userAccountService.command.infrastructure.imports;

import com.boriworld.boriPaw.userAccountService.command.domain.service.EmailCertificationCodeGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailCertificationCodeGeneratorImpl implements EmailCertificationCodeGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
