package com.boriworld.boriPaw.userAccountService.command.domain.model;

import lombok.Getter;

@Getter
public final class JwtToken {
    private final String accessToken;
    private final String refreshToken;
    public JwtToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static JwtToken generate(UserAccount userAccount) {

        return new JwtToken(null,null);
    }
}
