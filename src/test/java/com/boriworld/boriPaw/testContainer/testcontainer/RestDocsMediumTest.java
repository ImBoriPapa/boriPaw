package com.boriworld.boriPaw.testContainer.testcontainer;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.RestDocumentationContextProvider;

import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ActiveProfiles("test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsMediumTest extends MediumTestContainerRunner{
    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper();
    public static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris().removePort(),
                prettyPrint()
        );
    }

    public static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(
                modifyUris().removePort(),
                prettyPrint());
    }

    @BeforeEach
    void setMockMvc(WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(provider)
                        .uris()
                        .withScheme("https")
                        .withHost("server.boripaw.com")
                        .withPort(8080))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }
}
