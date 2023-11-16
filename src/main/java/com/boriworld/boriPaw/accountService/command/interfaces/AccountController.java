package com.boriworld.boriPaw.accountService.command.interfaces;

import com.boriworld.boriPaw.accountService.command.application.AccountCreateService;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import com.boriworld.boriPaw.accountService.command.interfaces.dto.AccountCreateResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountCreateService accountCreateService;
    @PostMapping("/accounts")
    public ResponseEntity<AccountCreateResponse> createAccount(@RequestBody AccountCreate accountCreate) {
        log.info("Controller Call");
        AccountId accountId = accountCreateService.processAccountCreation(accountCreate);

        return ResponseEntity
                .created(URI.create("/accounts/" + accountId.getId()))
                .body(new AccountCreateResponse(accountId.getId()));
    }
}
