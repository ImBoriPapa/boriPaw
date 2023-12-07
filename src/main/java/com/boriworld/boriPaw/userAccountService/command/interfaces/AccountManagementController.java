package com.boriworld.boriPaw.userAccountService.command.interfaces;

import com.boriworld.boriPaw.userAccountService.command.application.UserAccountManagementService;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.AccountCreateRequest;
import com.boriworld.boriPaw.userAccountService.command.interfaces.response.AccountCreateResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.boriworld.boriPaw.common.constant.ApiEndpoints.ACCOUNTS_ROOT_PATH;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountManagementController {
    private final UserAccountManagementService userAccountManagementService;

    @PostMapping(ACCOUNTS_ROOT_PATH)
    public ResponseEntity<AccountCreateResponse> createAccount(@RequestBody AccountCreateRequest accountCreateRequest) {
        log.info("Account create Request");
        UserAccountId userAccountId = userAccountManagementService.processUserAccountCreation(accountCreateRequest.toAccountCreate());

        return ResponseEntity
                .created(URI.create("/accounts/" + userAccountId.getId()))
                .body(new AccountCreateResponse(userAccountId.getId()));
    }
}
