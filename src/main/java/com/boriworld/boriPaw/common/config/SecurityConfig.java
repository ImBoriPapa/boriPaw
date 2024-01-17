package com.boriworld.boriPaw.common.config;

import com.boriworld.boriPaw.userAccountService.command.handler.AuthenticationTokenExceptionHandler;
import com.boriworld.boriPaw.userAccountService.command.handler.JwtAccessDeniedHandler;
import com.boriworld.boriPaw.userAccountService.command.handler.JwtAuthenticationEntryPoint;
import com.boriworld.boriPaw.userAccountService.command.handler.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.boriworld.boriPaw.common.constant.ApiEndpoints.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final String[] WHITE_LIST = {"/document/**", "/test", "/resources/**", "/greeting", "/favicon.ico", "/*/icon-*", "/css/**", "/js/**", "/images", "/fonts/**"};
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final AuthenticationTokenExceptionHandler authenticationTokenExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(apiConfigurationSource()))
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

    private CorsConfigurationSource apiConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
