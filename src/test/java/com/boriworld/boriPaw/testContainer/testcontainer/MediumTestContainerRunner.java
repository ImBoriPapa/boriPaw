package com.boriworld.boriPaw.testContainer.testcontainer;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;

import java.io.File;
import java.time.Duration;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class MediumTestContainerRunner {
    @Container
    protected static DockerComposeContainer<?> container;

    @Autowired
    TestDataCleaner cleaner;

    static {
        container = new DockerComposeContainer<>(new File("src/test/resources/docker-compose-test.yml"))
                .withExposedService("mysql", 3306)
                .waitingFor("mysql", Wait.forLogMessage(".*MySQL init process done.*", 1).withStartupTimeout(Duration.ofMinutes(2)))
                .withExposedService("redis", 6379)
                .waitingFor("redis", Wait.forLogMessage(".*Ready to accept connections.*", 1).withStartupTimeout(Duration.ofMinutes(2)));
        container.start();
    }

    @BeforeEach
    void clean() {
        cleaner.clean();
    }
}
