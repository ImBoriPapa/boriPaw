package com.boriworld.boriPaw.accountService.command.domain.service;

public interface AccountPasswordEncoder {
    String encode(String password);
    boolean isMatch(String encodedPassword, String rawPassword);
}
