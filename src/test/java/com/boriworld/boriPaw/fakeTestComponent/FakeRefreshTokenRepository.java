package com.boriworld.boriPaw.fakeTestComponent;

import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.RefreshTokenRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RefreshTokenId;

import java.util.Optional;

public class FakeRefreshTokenRepository implements RefreshTokenRepository {
    @Override
    public void save(RefreshToken refreshToken) {

    }

    @Override
    public Optional<RefreshToken> findRefreshTokenId(RefreshTokenId refreshTokenId) {
        return Optional.empty();
    }

    @Override
    public void delete(RefreshToken refreshToken) {


    }
}
