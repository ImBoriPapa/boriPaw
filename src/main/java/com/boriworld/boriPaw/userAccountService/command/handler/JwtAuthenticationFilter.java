package com.boriworld.boriPaw.userAccountService.command.handler;

import com.boriworld.boriPaw.common.constant.AuthenticationTokenHeaders;
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
        String tokenString = resolveAccessToken(request);
        if (tokenString != null) {
            userAccountAuthenticationService.processAuthenticationByAccessToken(tokenString);
        }

        filterChain.doFilter(request, response);
    }


    private String resolveAccessToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(AuthenticationTokenHeaders.AUTHORIZATION_HEADER);

        if (bearerToken != null && bearerToken.startsWith(AuthenticationTokenHeaders.ACCESS_TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
