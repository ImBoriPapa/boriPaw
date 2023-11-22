package com.boriworld.boriPaw.accountService.command.interfaces;

import com.boriworld.boriPaw.accountService.command.application.AccountCertificationService;
import com.boriworld.boriPaw.accountService.command.interfaces.request.EmailCertificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountCertificationController {

    private final AccountCertificationService accountCertificationService;
    @PostMapping("/accounts/email-certification")
    public ResponseEntity<EmailCertificationResponse> certify(@RequestBody EmailCertificationRequest emailCertificationRequest) {

        accountCertificationService.sendCertificationEmail(emailCertificationRequest.toEmailCertification());

        return ResponseEntity
                .ok()
                .body(new EmailCertificationResponse());
    }
}
