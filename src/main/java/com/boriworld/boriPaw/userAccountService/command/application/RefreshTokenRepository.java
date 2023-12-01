package com.boriworld.boriPaw.userAccountService.command.application;

import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;

public interface RefreshTokenRepository {
    public RefreshToken save(RefreshToken refreshToken);
}
