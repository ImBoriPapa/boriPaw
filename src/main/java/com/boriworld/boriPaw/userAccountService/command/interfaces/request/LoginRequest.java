package com.boriworld.boriPaw.userAccountService.command.interfaces.request;

public record LoginRequest(
        String email,
        String password) {
}
