package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.model.RefreshToken;

public interface RefreshTokenRepository {
    public RefreshToken save(RefreshToken refreshToken);
}
