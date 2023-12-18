package com.boriworld.boriPaw.userAccountService.query.interfaces;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountPrincipal;
import com.boriworld.boriPaw.userAccountService.query.repository.UserAccountQueryRepository;
import com.boriworld.boriPaw.userAccountService.query.response.UserAccountMe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAccountQueryController {

    private final UserAccountQueryRepository queryRepository;

    @GetMapping(ApiEndpoints.ME)
    public ResponseEntity getMe(@AuthenticationPrincipal UserAccountPrincipal principal) {

        log.info("userId: {}", principal.userAccountId().getId());
        log.info("authority: {}", principal.authority().name());
        Optional<UserAccountMe> accountMe = queryRepository.get(principal.userAccountId().getId());
        return ResponseEntity.ok().body(accountMe.get());
    }
}
