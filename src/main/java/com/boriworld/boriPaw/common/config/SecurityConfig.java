package com.boriworld.boriPaw.common.config;

import com.boriworld.boriPaw.userAccountService.command.handler.AuthenticationTokenExceptionHandler;
import com.boriworld.boriPaw.userAccountService.command.handler.JwtAccessDeniedHandler;
import com.boriworld.boriPaw.userAccountService.command.handler.JwtAuthenticationEntryPoint;
import com.boriworld.boriPaw.userAccountService.command.handler.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.boriworld.boriPaw.common.constant.ApiEndpoints.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final String[] WHITE_LIST = {"/test", "/docs/**","/greeting","/favicon.ico"};
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final AuthenticationTokenExceptionHandler authenticationTokenExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(d -> d.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(matchers -> matchers
                        .requestMatchers(WHITE_LIST)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, ACCOUNTS_ROOT_PATH, LOGIN_PATH, RE_ISSUE_PATH, LOGOUT_PATH)
                        .permitAll()
                        .anyRequest()
                        .authenticated())

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authenticationTokenExceptionHandler, JwtAuthenticationFilter.class)

                .exceptionHandling(
                        handle -> handle
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .build();
    }
}
