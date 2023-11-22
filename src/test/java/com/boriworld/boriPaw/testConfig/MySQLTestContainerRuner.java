package com.boriworld.boriPaw.testConfig;


import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.test.context.ActiveProfiles;


import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;


@Disabled
@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class MySQLTestContainerRuner {
    final static DockerComposeContainer<?> compose;

    static {
        compose = new DockerComposeContainer(new File("src/test/resources/docker-compose-mysql.yml"));
        compose.withExposedService("mysql", 3306);
        compose.start();
    }
}
