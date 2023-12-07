package com.boriworld.boriPaw.userAccountService.command.handler;

import com.boriworld.boriPaw.userAccountService.command.application.UserAccountAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserAccountAuthenticationService userAccountAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveAccessToken(request);

        userAccountAuthenticationService.processAuthenticationByAccessToken(token);

        filterChain.doFilter(request, response);
    }


    private String resolveAccessToken(HttpServletRequest request) {
        //before develop
        return "Not Yet";
    }
}
