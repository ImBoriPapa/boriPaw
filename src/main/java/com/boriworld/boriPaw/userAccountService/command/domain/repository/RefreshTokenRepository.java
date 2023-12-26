package com.boriworld.boriPaw.userAccountService.command.domain.repository;


import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RefreshTokenId;

import java.util.Optional;

public interface RefreshTokenRepository {
    void save(RefreshToken refreshToken);
    Optional<RefreshToken> findRefreshTokenId(RefreshTokenId refreshTokenId);
    void delete(RefreshToken refreshToken);
}
