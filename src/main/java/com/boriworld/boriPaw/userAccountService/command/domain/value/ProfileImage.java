package com.boriworld.boriPaw.userAccountService.command.domain.value;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class ProfileImage {
    private final String savedPath;
    private final String fullPath;
    private final String contentType;
    private final LocalDateTime uploadedAt;

    private ProfileImage(String savedPath, String fullPath, String contentType, LocalDateTime uploadedAt) {
        this.savedPath = savedPath;
        this.fullPath = fullPath;
        this.contentType = contentType;
        this.uploadedAt = uploadedAt;
    }

    public static ProfileImage of(String savedPath,String fullPath,String contentType,LocalDateTime uploadedAt) {
        return new ProfileImage(savedPath,fullPath,contentType,uploadedAt);
    }
}
