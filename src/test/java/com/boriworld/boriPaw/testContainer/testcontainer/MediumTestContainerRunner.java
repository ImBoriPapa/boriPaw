package com.boriworld.boriPaw.testContainer.testcontainer;


import lombok.extern.slf4j.Slf4j;


import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;


import java.io.File;
import java.time.Duration;
@Slf4j
public abstract class MediumTestContainerRunner {
    protected static DockerComposeContainer<?> container;

    static {
        container = new DockerComposeContainer<>(new File("src/test/resources/docker-compose-test.yml"))
                .withExposedService("mysql", 3306)
                .waitingFor("mysql", Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
                .withExposedService("redis", 6379)
                .waitingFor("redis", Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));
        container.start();
    }



}
