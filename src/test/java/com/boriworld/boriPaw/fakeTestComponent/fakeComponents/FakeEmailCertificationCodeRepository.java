package com.boriworld.boriPaw.fakeTestComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.repository.EmailCertificationCodeRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.model.EmailCertificationCode;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class FakeEmailCertificationCodeRepository implements EmailCertificationCodeRepository {

    private final Map<String, EmailCertificationCode> store = new ConcurrentHashMap<>();

    @Override
    public EmailCertificationCode save(EmailCertificationCode emailCertificationCode) {
        String generatedId = UUID.randomUUID().toString();

        EmailCertificationCode initializedEmailCertificationCode = EmailCertificationCode
                .initialize(generatedId, emailCertificationCode.getEmail(), emailCertificationCode.getCode(), emailCertificationCode.getExpirationTime());

        store.put(generatedId, initializedEmailCertificationCode);

        return initializedEmailCertificationCode;
    }
}
