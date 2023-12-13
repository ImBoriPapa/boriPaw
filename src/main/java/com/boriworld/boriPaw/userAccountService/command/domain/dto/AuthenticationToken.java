package com.boriworld.boriPaw.userAccountService.command.domain.dto;


import com.boriworld.boriPaw.userAccountService.command.domain.model.AccessToken;
import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;

public record AuthenticationToken(AccessToken accessToken, RefreshToken refreshToken) {
}
