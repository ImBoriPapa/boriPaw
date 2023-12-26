package com.boriworld.boriPaw.userAccountService.command.handler;

import com.boriworld.boriPaw.common.constant.AuthenticationTokenHeaderNames;
import com.boriworld.boriPaw.userAccountService.command.application.UserAccountAuthenticationService;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserAccountAuthenticationService userAccountAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("authentication filter");
        String tokenString = resolveAccessToken(request);


        if (tokenString != null) {
            userAccountAuthenticationService.processAuthenticationByAccessToken(tokenString);
        }


        filterChain.doFilter(request, response);
    }


    private String resolveAccessToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(AuthenticationTokenHeaderNames.AUTHORIZATION_HEADER);

        if (bearerToken != null && bearerToken.startsWith(AuthenticationTokenHeaderNames.ACCESS_TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
