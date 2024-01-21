package com.boriworld.boriPaw.userAccountService.command.interfaces.controller;

import com.boriworld.boriPaw.common.constant.ApiEndpoints;
import com.boriworld.boriPaw.common.exception.MultipartFileNotFoundException;
import com.boriworld.boriPaw.common.exception.NoResourceUpdatePrivilegesException;
import com.boriworld.boriPaw.common.validator.RequestBodyFieldsConstraintValidator;
import com.boriworld.boriPaw.userAccountService.command.application.UserProfileService;
import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountPrincipal;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.interfaces.request.UserNicknameChangeRequest;
import com.boriworld.boriPaw.userAccountService.command.interfaces.response.UserProfileUpdateResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final RequestBodyFieldsConstraintValidator validator;

    @PatchMapping(ApiEndpoints.CHANGE_PROFILE_NICKNAME)
    public ResponseEntity<UserProfileUpdateResponse> changeNickname(@AuthenticationPrincipal UserAccountPrincipal principal,
                                                                    @RequestBody UserNicknameChangeRequest request,
                                                                    @PathVariable(name = "user-accountsId") Long userAccountId) {
        log.info("user profile nickname change request new nickname is {}", request.getNickname());

        validatePrivileges(principal, userAccountId);

        validator.validate(request);

        UserAccountId updated = userProfileService.processingUpdateNickname(UserAccountId.of(userAccountId), request.getNickname());

        return ResponseEntity.ok(new UserProfileUpdateResponse(updated.getId()));
    }

    @PatchMapping(ApiEndpoints.CHANGE_PROFILE_INTRODUCE)
    public ResponseEntity<UserProfileUpdateResponse> changeIntroduce(@AuthenticationPrincipal UserAccountPrincipal principal,
                                                                     @RequestBody UserIntroduceChangeRequest request,
                                                                     @PathVariable(name = "user-accountsId") Long userAccountId) {
        log.info("user profile nickname change request new introduce is {}", request.getIntroduce());
        validatePrivileges(principal, userAccountId);

        UserAccountId updated = userProfileService.processingUpdateIntroduce(UserAccountId.of(userAccountId), request.getIntroduce());

        return ResponseEntity.ok(new UserProfileUpdateResponse(updated.getId()));
    }

    @PutMapping(ApiEndpoints.CHANGE_PROFILE_IMAGE)
    public ResponseEntity<UserProfileUpdateResponse> profileImage(@RequestPart(name = "file", required = false) MultipartFile file,
                                                                  @PathVariable(name = "user-accountsId") Long userAccountId) {
        log.info("user profile image update request");
        if (file == null) {
            throw MultipartFileNotFoundException.forMessage("프로필 이미지 변경 요청시 Multipart file name: file 이 필요합니다.");
        }
        UserAccountId updateProfileImage = userProfileService.updateProfileImage(UserAccountId.of(userAccountId), file);

        return ResponseEntity.ok()
                .body(new UserProfileUpdateResponse(updateProfileImage.getId()));
    }

    private void validatePrivileges(UserAccountPrincipal principal, Long userAccountId) {
        if (!principal.userAccountId().getId().equals(userAccountId)) {
            throw NoResourceUpdatePrivilegesException.forMessage("해당 프로필 닉네임에 수정 권한이 없는 사용자입니다.");
        }
    }
}
