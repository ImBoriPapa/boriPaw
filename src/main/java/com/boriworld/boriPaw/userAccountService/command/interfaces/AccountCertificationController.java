package com.boriworld.boriPaw.userAccountService.command.interfaces;

import com.boriworld.boriPaw.userAccountService.command.application.AccountAuthenticationService;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.EmailCertificationRequest;
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

    private final AccountAuthenticationService accountAuthenticationService;
    @PostMapping("/accounts/email-certification")
    public ResponseEntity<EmailCertificationResponse> certify(@RequestBody EmailCertificationRequest emailCertificationRequest) {

        accountAuthenticationService.sendCertificationEmail(emailCertificationRequest.toEmailCertification());

        return ResponseEntity
                .ok()
                .body(new EmailCertificationResponse());
    }
}
