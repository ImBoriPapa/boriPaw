package com.boriworld.boriPaw.userAccountService.command.domain.service;

public interface UserAccountPasswordEncoder {
    String encode(String password);
    boolean isMatch(String encodedPassword, String rawPassword);
}
