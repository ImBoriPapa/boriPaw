package com.boriworld.boriPaw.userAccountService.command.interfaces.controller;

import com.boriworld.boriPaw.common.validator.RequestConstraintValidator;
import com.boriworld.boriPaw.userAccountService.command.application.UserAccountManagementService;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.UserAccountCreateRequest;
import com.boriworld.boriPaw.userAccountService.command.interfaces.response.UserAccountCreateResponse;
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
public class UserAccountManagementController {
    private final UserAccountManagementService userAccountManagementService;
    private final RequestConstraintValidator validator;

    @PostMapping(ACCOUNTS_ROOT_PATH)
    public ResponseEntity<UserAccountCreateResponse> createUserAccount(@RequestBody UserAccountCreateRequest userAccountCreateRequest) {
        log.info("Account create Request");
        validator.validate(userAccountCreateRequest);
        UserAccountId userAccountId = userAccountManagementService.processUserAccountCreation(userAccountCreateRequest.toUserAccountCreate());

        return ResponseEntity
                .created(URI.create(ACCOUNTS_ROOT_PATH + "/" + userAccountId.getId()))
                .body(new UserAccountCreateResponse(userAccountId.getId()));
    }

}
