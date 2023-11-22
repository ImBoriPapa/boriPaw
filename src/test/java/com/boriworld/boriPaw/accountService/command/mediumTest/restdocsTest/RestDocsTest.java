package com.boriworld.boriPaw.accountService.command.mediumTest.restdocsTest;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;

import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RestDocsTest {
    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper();
    static DockerComposeContainer<?> dockerComposeContainer;

    static {
        File mysql = new File("src/test/resources/docker-compose-mysql.yml");
        File redis = new File("src/test/resources/docker-compose-redis.yml");
        List<File> files = List.of(mysql, redis);
        dockerComposeContainer = new DockerComposeContainer<>(files);
    }

    @BeforeAll
    static void setUpInfraStructure() {
        dockerComposeContainer.withExposedService("mysql", 3306);
        dockerComposeContainer.withExposedService("redis", 6379);
        dockerComposeContainer.start();
    }

    @BeforeEach
    void setMockMvc(WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider) {
        System.out.println("[Setup MockMVC For RestDocs Test]");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(provider)
                        .uris()
                        .withScheme("http")
                        .withScheme("localhost")
                        .withPort(8080))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @AfterEach
    void cleanDatabase() {
        System.out.println("Clean Database");
    }

    @AfterAll
    static void tearDownInfraStructure() {
        dockerComposeContainer.stop();
        System.out.println("Stop TestContainer");
    }
}
