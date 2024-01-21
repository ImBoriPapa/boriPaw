package com.boriworld.boriPaw.testContainer.testcontainer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@TestConfiguration
@ActiveProfiles("test")
public class LocalStackConfig {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;
    @Value("${aws.secretKey}")
    private String secreteKey;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private static final DockerImageName LOCALSTACK_IMAGE = DockerImageName.parse("localstack/localstack");

    // GenericContainer start(), stop() 메서드로 생명주기 설정
    @Bean(initMethod = "start", destroyMethod = "stop")
    public LocalStackContainer localStackContainer() {
        return new LocalStackContainer(LOCALSTACK_IMAGE)
                .withServices(S3);
    }

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return AwsCredentialsProviderChain.builder()
                .reuseLastProviderEnabled(true)
                .credentialsProviders(
                        List.of(
                                DefaultCredentialsProvider.create(),
                                StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId,secreteKey))
                        ))
                .build();
    }

    @Bean
    public S3Client s3Client(LocalStackContainer localStackContainer) {
        S3Client s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(localStackContainer.getEndpointOverride(S3))
                .credentialsProvider(awsCredentialsProvider())
                .forcePathStyle(true)
                .build();
        s3Client.createBucket(builder -> builder.bucket(bucketName));

        return s3Client;
    }

}
