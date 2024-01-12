package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

/**
 * 애플리케이션에서 도메인 모델에 요청을 위한 객체입니다.
 *
 * @param email
 * @param password
 * @param nickname
 */

public record UserAccountCreate(String email, String password, String nickname){
}
