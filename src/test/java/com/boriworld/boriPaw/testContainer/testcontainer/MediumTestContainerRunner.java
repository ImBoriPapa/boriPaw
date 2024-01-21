package com.boriworld.boriPaw.testContainer.testcontainer;


import lombok.extern.slf4j.Slf4j;


import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


import java.io.File;
import java.time.Duration;
import java.util.List;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Slf4j
@ActiveProfiles("test")
public abstract class MediumTestContainerRunner {
    private static final DockerComposeContainer<?> container;

    static {
        container = new DockerComposeContainer<>(new File("src/test/resources/docker-compose-test.yml"))
                .withExposedService("mysql", 3306)
                .waitingFor("mysql", Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
                .withExposedService("redis", 6379)
                .waitingFor("redis", Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));
        container.start();
    }
}
