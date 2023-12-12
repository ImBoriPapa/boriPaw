package com.boriworld.boriPaw.userAccountService.command.domain.service;

public interface AuthenticationTokenPayloadEncoder{
    String encode(String payloadValue);
    String decode(String payloadValue);
}
