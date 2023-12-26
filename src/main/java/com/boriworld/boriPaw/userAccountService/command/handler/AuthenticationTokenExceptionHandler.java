package com.boriworld.boriPaw.userAccountService.command.handler;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;
import com.boriworld.boriPaw.common.factory.ErrorResponseFactory;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.AuthenticationTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationTokenExceptionHandler extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final ErrorResponseFactory factory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationTokenException e) {
            log.error("handle 'AuthenticationTokenException' message: {}", e.getMessage());
            response.setStatus(e.getStatus().value());
            response.getWriter().write(objectMapper.writeValueAsString(factory.problemDetail(e)));
        }
    }
}
