package com.boriworld.boriPaw.accountService.command.infrastructure.imports;

import com.boriworld.boriPaw.accountService.command.domain.service.AccountPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountPasswordEncoderImpl implements AccountPasswordEncoder {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean isMatch(String encodedPassword, String rawPassword) {
        return passwordEncoder.matches(encodedPassword, rawPassword);
    }
}
