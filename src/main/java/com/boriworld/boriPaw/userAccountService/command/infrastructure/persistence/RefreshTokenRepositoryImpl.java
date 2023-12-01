package com.boriworld.boriPaw.accountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.accountService.command.application.RefreshTokenRepository;
import com.boriworld.boriPaw.accountService.command.domain.model.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return null;
    }
}
