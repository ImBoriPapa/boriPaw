package com.boriworld.boriPaw.accountService.command.domain;

public interface AccountPasswordEncoder {
    String encode(String password);

    boolean isMatch(String encodedPassword, String rawPassword);
}
