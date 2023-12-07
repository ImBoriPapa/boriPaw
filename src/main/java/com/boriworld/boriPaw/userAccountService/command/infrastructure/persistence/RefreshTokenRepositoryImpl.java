package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.repository.RefreshTokenRepository;

import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RefreshTokenId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return null;
    }

    @Override
    public Optional<RefreshToken> findById(RefreshTokenId refreshTokenId) {
        return Optional.empty();
    }
}
