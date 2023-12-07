package com.boriworld.boriPaw.userAccountService.command.domain.model;


import com.boriworld.boriPaw.userAccountService.command.domain.value.AccessToken;


public record AuthenticationToken(AccessToken accessToken, RefreshToken refreshToken) {
}
