package com.boriworld.boriPaw.testContanier;

import com.boriworld.boriPaw.accountService.command.domain.service.EmailCertificationCodeGenerator;

public class FakeEmailCertificationCodeGenerator implements EmailCertificationCodeGenerator {
    @Override
    public String generate() {
        return "1234";
    }
}
