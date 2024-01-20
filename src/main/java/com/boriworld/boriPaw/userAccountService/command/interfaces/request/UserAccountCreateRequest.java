package com.boriworld.boriPaw.userAccountService.command.interfaces.request;

import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Controller 에서 Http 통신을 위한 객체입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountCreateRequest {
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    private String email;
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    private String password;
    @Size(min = 4,message = "닉네임은 최소 4자리 이상이어야 합니다.")
    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    private String nickname;

    public UserAccountCreate toUserAccountCreate() {
        return new UserAccountCreate(email, password, nickname);
    }
}
