package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.dto.SendCertificationEmail;
import com.boriworld.boriPaw.accountService.command.domain.model.EmailCertificationCode;
import com.boriworld.boriPaw.accountService.command.domain.repository.EmailCertificationCodeRepository;
import com.boriworld.boriPaw.accountService.command.domain.service.CertificationMailSender;
import com.boriworld.boriPaw.accountService.command.domain.service.EmailCertificationCodeGenerator;
import com.boriworld.boriPaw.accountService.command.interfaces.request.EmailCertification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCertificationService {
    private final EmailCertificationCodeGenerator emailCertificationCodeGenerator;
    private final EmailCertificationCodeRepository emailCertificationCodeRepository;
    private final CertificationMailSender certificationMailSender;

    @Transactional
    public void sendCertificationEmail(SendCertificationEmail sendCertificationEmail) {
        EmailCertificationCode certificationCode = emailCertificationCodeRepository.save(EmailCertificationCode.create(sendCertificationEmail.email(), emailCertificationCodeGenerator));

        certificationMailSender.send();
    }

    public boolean certifyEmail(EmailCertification emailCertification) {
        return true;
    }
}
