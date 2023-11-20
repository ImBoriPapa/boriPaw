package com.boriworld.boriPaw.accountService.command.interfaces;

import com.boriworld.boriPaw.accountService.command.application.AccountCreateService;

import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import com.boriworld.boriPaw.accountService.command.interfaces.request.AccountCreateRequest;
import com.boriworld.boriPaw.accountService.command.interfaces.response.AccountCreateResponse;
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
    public ResponseEntity<AccountCreateResponse> createAccount(@RequestBody AccountCreateRequest accountCreateRequest) {
        log.info("Controller Call");
        AccountId accountId = accountCreateService.processAccountCreation(accountCreateRequest.toAccountCreate());

        return ResponseEntity
                .created(URI.create("/accounts/" + accountId.getId()))
                .body(new AccountCreateResponse(accountId.getId()));
    }
}
