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
    private String email;
    @NotBlank(message = "유저네임을 입력해주세요")
    private String username;
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;
    @NotBlank(message = "별명은 비어있을 수 없습니다.")
    private String nickname;

    public UserAccountCreate toUserAccountCreate() {
        return new UserAccountCreate(email, username, password, nickname);
    }
}
