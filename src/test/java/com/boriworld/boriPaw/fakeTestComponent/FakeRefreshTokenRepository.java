package com.boriworld.boriPaw.fakeTestComponent;

import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.RefreshTokenRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RefreshTokenId;

import java.util.Optional;

public class FakeRefreshTokenRepository implements RefreshTokenRepository {
    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return null;
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenId(RefreshTokenId refreshTokenId) {
        return Optional.empty();
    }
}
