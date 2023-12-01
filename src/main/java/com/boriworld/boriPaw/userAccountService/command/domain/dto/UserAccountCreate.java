package com.boriworld.boriPaw.accountService.command.domain.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountCreate(
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        String email,
        @NotBlank(message = "계정 이름은 비어있을 수 없습니다.")
        String accountName,
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password,
        @NotBlank(message = "별명은 비어있을 수 없습니다.")
        String nickname
) {
}
