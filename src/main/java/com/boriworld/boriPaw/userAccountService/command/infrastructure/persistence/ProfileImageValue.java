package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.value.ProfileImage;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImageValue {
    private String savedPath;
    private String fullPath;
    private String contentType;
    private LocalDateTime uploadedAt;

    protected ProfileImageValue(String savedPath, String fullPath, String contentType, LocalDateTime uploadedAt) {
        this.savedPath = savedPath;
        this.fullPath = fullPath;
        this.contentType = contentType;
        this.uploadedAt = uploadedAt;
    }

    public static ProfileImageValue form(ProfileImage profileImage) {
        return new ProfileImageValue(profileImage.getSavedPath(), profileImage.getFullPath(), profileImage.getContentType(), profileImage.getUploadedAt());
    }

    public ProfileImage to() {
        return ProfileImage.of(this.savedPath, this.fullPath, this.contentType, this.uploadedAt);
    }
}
