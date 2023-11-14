package com.boriworld.boriPaw.accountService.command.domain;

public class FakeAccountPasswordEncoder implements AccountPasswordEncoder {

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
