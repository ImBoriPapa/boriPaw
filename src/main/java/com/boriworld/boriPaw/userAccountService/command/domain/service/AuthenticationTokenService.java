package com.boriworld.boriPaw.userAccountService.command.domain.service;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;

import com.boriworld.boriPaw.userAccountService.command.domain.value.AccessTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenType;

import java.util.Date;

/**
 * Domain Service
 */
public interface AuthenticationTokenService {
    String generateToken(AuthenticationTokenCredentials authenticationTokenCredentials, AuthenticationTokenType tokenType);
    AccessTokenStatus validateToken(String token);
    String getSubject(String token);
    Object getClaim(String token,String value);
    Date getExpiredDate(String generateToken);
}
