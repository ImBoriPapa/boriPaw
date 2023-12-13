package com.boriworld.boriPaw.userAccountService.command.domain.service;

/**
 * Domain Service
 * AuthenticationToken 에 암호화가 필요한 페이로드를 암호화 및 해독
 */
public interface AuthenticationTokenPayloadEncoder {
    String encode(String payloadValue);
    String decode(String payloadValue);
}
