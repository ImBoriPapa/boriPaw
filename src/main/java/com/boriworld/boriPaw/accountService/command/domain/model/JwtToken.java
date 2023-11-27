package com.boriworld.boriPaw.accountService.command.domain.model;

import lombok.Getter;

@Getter
public final class JwtToken {
    private final String accessToken;
    private final String refreshToken;
    public JwtToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static JwtToken generate(Account account) {

        return new JwtToken(null,null);
    }
}
