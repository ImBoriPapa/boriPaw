package com.boriworld.boriPaw.accountService.command.domain;

import com.boriworld.boriPaw.accountService.command.domain.service.EmailCertificationCodeGenerator;

public class FakeEmailCertificationCodeGenerator implements EmailCertificationCodeGenerator {
    @Override
    public String generate() {
        return "1234";
    }
}
