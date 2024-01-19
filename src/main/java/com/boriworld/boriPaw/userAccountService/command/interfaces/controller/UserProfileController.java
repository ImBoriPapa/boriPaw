package com.boriworld.boriPaw.userAccountService.command.interfaces.controller;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.common.exception.NoResourceUpdatePrivilegesException;
import com.boriworld.boriPaw.userAccountService.command.application.UserProfileService;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountPrincipal;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.UserNicknameChangeRequest;
import com.boriworld.boriPaw.userAccountService.command.interfaces.response.UserProfileUpdateResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PatchMapping(ApiEndpoints.CHANGE_PROFILE_NICKNAME)
    public ResponseEntity changeNickname(@AuthenticationPrincipal UserAccountPrincipal principal,
                                         @RequestBody UserNicknameChangeRequest request,
                                         @PathVariable(name = "user-accountsId") Long userAccountId) {
        log.info("user profile nickname change request new nickname is {}", request.getNickname());

        if (!principal.userAccountId().getId().equals(userAccountId)) {
            throw NoResourceUpdatePrivilegesException.forMessage("해당 프로필 닉네임에 수정 권한이 없는 사용자입니다.");
        }

        UserAccountId updated = userProfileService.updateNickname(UserAccountId.of(userAccountId), request.getNickname());

        return ResponseEntity.ok(new UserProfileUpdateResponse(updated.getId()));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class RequestForm {
        private String nickname;
    }
}
