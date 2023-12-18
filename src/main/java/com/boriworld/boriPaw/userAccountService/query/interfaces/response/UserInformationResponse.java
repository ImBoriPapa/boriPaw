package com.boriworld.boriPaw.userAccountService.query.interfaces.response;

import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserInformation;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;


public record UserInformationResponse(Long userAccountId,
                                      Authority authority,
                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul") LocalDateTime lastLoginAt) {
    public UserInformationResponse(Long userAccountId, Authority authority, LocalDateTime lastLoginAt) {
        this.userAccountId = userAccountId;
        this.authority = authority;
        this.lastLoginAt = lastLoginAt;
    }

    public static UserInformationResponse from(UserInformation userInformation) {
        return new UserInformationResponse(userInformation.getUserAccountId(), userInformation.getAuthority(), userInformation.getLastLoginAt());
    }
}
