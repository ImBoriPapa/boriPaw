package com.boriworld.boriPaw.userAccountService.command.application.dto;

public record LoginProcessRequest(
        String email,
        String password
) {
}
