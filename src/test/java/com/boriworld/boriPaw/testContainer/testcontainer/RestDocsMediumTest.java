package com.boriworld.boriPaw.testContainer.testcontainer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
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

import java.nio.charset.Charset;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;


@ActiveProfiles("test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsMediumTest extends MediumTestContainerRunner {
    @Autowired
    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    public static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .removePort()
                        .scheme("https")
                        .host("server.boripaw.com"),
                prettyPrint()

        );
    }

    public static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(
                modifyUris()
                        .removePort()
                        .scheme("https")
                        .host("server.boripaw.com"),
                prettyPrint());
    }
}
