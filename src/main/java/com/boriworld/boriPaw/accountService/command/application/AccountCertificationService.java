package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.dto.SendCertificationEmail;
import com.boriworld.boriPaw.accountService.command.domain.model.EmailCertificationCode;
import com.boriworld.boriPaw.accountService.command.domain.repository.EmailCertificationCodeRepository;
import com.boriworld.boriPaw.accountService.command.domain.service.CertificationMailSender;
import com.boriworld.boriPaw.accountService.command.domain.service.EmailCertificationCodeGenerator;
import com.boriworld.boriPaw.accountService.command.interfaces.request.EmailCertification;
import com.boriworld.boriPaw.accountService.command.interfaces.request.EmailCertificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCertificationService {
    private final EmailCertificationCodeGenerator emailCertificationCodeGenerator;
    private final EmailCertificationCodeRepository emailCertificationCodeRepository;
    private final CertificationMailSender certificationMailSender;

    @Transactional
    public void sendCertificationEmail(EmailCertification certification) {
        EmailCertificationCode certificationCode = emailCertificationCodeRepository.save(EmailCertificationCode.create(certification.email(), emailCertificationCodeGenerator));
        EmailCertification emailCertification = new EmailCertification(certification.email());
        certifyEmailAsync(emailCertification);
    }

    public void certifyEmailAsync(EmailCertification emailCertification) {
        try {
            CompletableFuture.runAsync(certificationMailSender::send)
                    .handle((result, throwable) -> {
                        if (throwable != null) {
                            return false;
                        } else {
                            return true;
                        }
                    });
        } catch (Exception e) {
            throw new IllegalArgumentException("이메일 발송 실패");
        }
    }
}
