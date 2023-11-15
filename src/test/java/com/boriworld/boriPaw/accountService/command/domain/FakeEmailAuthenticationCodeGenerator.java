package com.boriworld.boriPaw.accountService.command.domain;

import com.boriworld.boriPaw.accountService.command.domain.EmailAuthenticationCodeGenerator;

public class FakeEmailAuthenticationCodeGenerator implements EmailAuthenticationCodeGenerator {
    @Override
    public String generate() {
        return "1234";
    }
}
