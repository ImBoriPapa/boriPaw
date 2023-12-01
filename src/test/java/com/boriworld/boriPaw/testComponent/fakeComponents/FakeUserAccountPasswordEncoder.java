package com.boriworld.boriPaw.testComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;

public class FakeUserAccountPasswordEncoder implements UserAccountPasswordEncoder {

    private final String FAKE_ENCODE_PREFIX = "(ENCODED)";

    @Override
    public String encode(String password) {
        return FAKE_ENCODE_PREFIX + password;
    }

    @Override
    public boolean isMatch(String encodedPassword, String rawPassword) {

        return encodedPassword.equals(FAKE_ENCODE_PREFIX + rawPassword);
    }
}
