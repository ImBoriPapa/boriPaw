package com.boriworld.boriPaw.userAccountService.command.interfaces.request;

import com.boriworld.boriPaw.userAccountService.command.application.dto.EmailCertification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailCertificationRequest {
    private String email;

    public EmailCertification toEmailCertification() {
        return new EmailCertification(email);
    }

}
