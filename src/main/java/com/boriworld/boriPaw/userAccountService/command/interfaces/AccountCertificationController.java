package com.boriworld.boriPaw.userAccountService.command.interfaces;

import com.boriworld.boriPaw.userAccountService.command.application.UserAccountAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountCertificationController {

    private final UserAccountAuthenticationService userAccountAuthenticationService;

}
