package com.boriworld.boriPaw.userAccountService.command.domain.service;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.AuthenticationTokenCredentials;

import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenStatus;
import com.boriworld.boriPaw.userAccountService.command.domain.value.AuthenticationTokenType;


import java.util.Date;

/**
 * Domain Service
 */
public interface AuthenticationTokenService {
    String generateTokenString(AuthenticationTokenCredentials authenticationTokenCredentials, AuthenticationTokenType authenticationTokenType);
    AuthenticationTokenStatus validateToken(String token);
    String getSubject(String token);
    Object getClaim(String tokenString,String key);
    Date getExpiredDate(String generateToken);
}
