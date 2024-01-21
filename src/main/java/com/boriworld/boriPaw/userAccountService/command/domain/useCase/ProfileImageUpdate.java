package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

import java.time.LocalDateTime;

public record ProfileImageUpdate(
        String savedPath,
        String fullPath,
        String contentType,
        LocalDateTime uploadedAt
) {
}
