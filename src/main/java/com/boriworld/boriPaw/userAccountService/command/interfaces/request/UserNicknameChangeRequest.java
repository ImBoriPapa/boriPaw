package com.boriworld.boriPaw.userAccountService.command.interfaces.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNicknameChangeRequest {
    @Size(min = 4, message = "닉네임은 최소 4자리 이상이어야 합니다.")
    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    private String nickname;
}
