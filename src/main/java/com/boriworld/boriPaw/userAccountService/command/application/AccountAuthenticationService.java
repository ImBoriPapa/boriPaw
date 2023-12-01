package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.accountService.command.domain.model.EmailCertificationCode;
import com.boriworld.boriPaw.accountService.command.domain.model.JwtToken;
import com.boriworld.boriPaw.accountService.command.domain.model.RefreshToken;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.accountService.command.domain.repository.EmailCertificationCodeRepository;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountPasswordEncoder;
import com.boriworld.boriPaw.accountService.command.domain.service.CertificationMailSender;
import com.boriworld.boriPaw.accountService.command.domain.service.EmailCertificationCodeGenerator;
import com.boriworld.boriPaw.accountService.command.interfaces.request.EmailCertification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountAuthenticationService {

    private final AccountRepository accountRepository;
    private final AccountPasswordEncoder accountPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmailCertificationCodeGenerator emailCertificationCodeGenerator;
    private final EmailCertificationCodeRepository emailCertificationCodeRepository;
    private final CertificationMailSender certificationMailSender;

    @Transactional
    public void loginProcess(String email, String password) {
        UserAccount userAccount = accountRepository.findByEmail(email)
                .orElseThrow(() -> new LoginFailException("이메일을 확인을 해봐요"));

        UserAccount login = userAccount.login(password, accountPasswordEncoder);

        JwtToken jwtToken = JwtToken.generate(login);

        RefreshToken refreshToken = RefreshToken.of(jwtToken.getRefreshToken(), 36000);
        refreshTokenRepository.save(refreshToken);
    }

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
