package com.boriworld.boriPaw.accountService.command.domain;

import com.boriworld.boriPaw.accountService.command.application.EmailAuthenticationCodeRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class FakeEmailAuthenticationCodeRepository implements EmailAuthenticationCodeRepository {

    private final Map<String, EmailAuthenticationCode> store = new ConcurrentHashMap<>();

    @Override
    public EmailAuthenticationCode save(EmailAuthenticationCode emailAuthenticationCode) {
        String generatedId = UUID.randomUUID().toString();

        EmailAuthenticationCode initializedEmailAuthenticationCode = EmailAuthenticationCode
                .initialize(generatedId, emailAuthenticationCode.getEmail(), emailAuthenticationCode.getCode(), emailAuthenticationCode.getExpirationTime());

        store.put(generatedId, initializedEmailAuthenticationCode);

        return initializedEmailAuthenticationCode;
    }
}
