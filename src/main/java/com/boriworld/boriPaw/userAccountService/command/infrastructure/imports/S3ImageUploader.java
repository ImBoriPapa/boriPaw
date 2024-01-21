package com.boriworld.boriPaw.userAccountService.command.infrastructure.imports;

import com.boriworld.boriPaw.common.config.S3Config;
import com.boriworld.boriPaw.userAccountService.command.domain.service.ProfileImageManager;
import com.boriworld.boriPaw.userAccountService.command.domain.value.ProfileImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3ImageUploader implements ProfileImageManager {
    private final S3Client s3Client;
    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    @Override
    public ProfileImage uploadImage(MultipartFile multipartFile) {
        log.info("upload multipart file");
        String folderName = "user-profile-image/";
        String cloudFrontName = "https://d16sctm2j26rh0.cloudfront.net";
        String key = folderName + UUID.randomUUID();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();


        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("profile image upload fall");
        }

        String savedPath = bucketName + key;
        String fullPath = cloudFrontName + key;
        String contentType = multipartFile.getContentType();
        LocalDateTime uploadedAt = LocalDateTime.now();

        return ProfileImage.of(savedPath, fullPath, contentType, uploadedAt);
    }

    @Override
    public void delete(ProfileImage profileImage) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(profileImage.getSavedPath())
                .build();

        DeleteObjectResponse response = s3Client.deleteObject(request);

    }
}
