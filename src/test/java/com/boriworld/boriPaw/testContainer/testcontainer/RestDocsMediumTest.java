package com.boriworld.boriPaw.testContainer.testcontainer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;




import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RestDocsMediumTest extends MediumTestContainerRunner{
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private TestDataCleaner cleaner;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void clean() {
        cleaner.clean();
    }

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
