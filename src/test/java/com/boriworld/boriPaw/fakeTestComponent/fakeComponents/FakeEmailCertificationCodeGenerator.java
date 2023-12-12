package com.boriworld.boriPaw.fakeTestComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.service.EmailCertificationCodeGenerator;

public class FakeEmailCertificationCodeGenerator implements EmailCertificationCodeGenerator {
    @Override
    public String generate() {
        return "1234";
    }
}
