package com.boriworld.boriPaw.common.factory;

import com.boriworld.boriPaw.common.exception.CustomRuntimeException;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.AuthenticationTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class ErrorResponseFactory {
    private final String SERVER_HOST;
    public ErrorResponseFactory(@Value("${server.host}") final String SERVER_HOST) {
        this.SERVER_HOST = SERVER_HOST;
    }

    public ProblemDetail problemDetail(Exception e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        detail.setType(createTypeURI(e.getClass().getSimpleName()));
        detail.setTitle(e.getClass().getName());
        return detail;
    }

    public ProblemDetail problemDetail(CustomRuntimeException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
        detail.setType(createTypeURI(e.getType()));
        detail.setTitle(e.getTitle());
        return detail;
    }

    public ProblemDetail problemDetail(AuthenticationTokenException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
        detail.setType(createTypeURI(e.getType()));
        detail.setTitle(e.getTitle());
        return detail;
    }

    private URI createTypeURI(String type) {
        String path = "supports/errors/" + type;
        return UriComponentsBuilder
                .newInstance()
                .host(SERVER_HOST)
                .path(path)
                .build()
                .toUri();
    }

}
