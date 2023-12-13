package com.boriworld.boriPaw.common.config;

import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.userAccountService.command.infrastructure.imports.CipherAuthenticationTokenPayloadEncoder;
import com.boriworld.boriPaw.common.constant.EncodeAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationTokenPayloadEncoderConfig {
    @Bean
    public AuthenticationTokenPayloadEncoder payloadEncoder(@Value("${secrete.encoder.key}") String key,
                                                            @Value("${secrete.encoder.iv}") String iv) {
        return CipherAuthenticationTokenPayloadEncoder.builder()
                .secretKey(key)
                .initializationVector(iv)
                .encodeAlgorithm(EncodeAlgorithm.AES)
                .build();
    }
}
