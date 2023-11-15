package com.boriworld.boriPaw.accountService.command.domain;

import lombok.Getter;

@Getter
public final class EmailAuthenticationCode {
    private final String id;
    private final String email;
    private final String code;
    private final Long expirationTime;

    private EmailAuthenticationCode(String id, String email, String code, Long expirationTime) {
        this.id = id;
        this.email = email;
        this.code = code;
        this.expirationTime = expirationTime;
    }

    /**
     * role: 이메일 인증 코드 생성 및 검증
     * responsibility: 이메일 인증 코드 생성과 검증 및 저장소에 저장하기 위한 데이터 정의
     *
     * @param email:                            user email
     * @param emailAuthenticationCodeGenerator: 인증 코드 생성기
     * @return EmailAuthenticationCode: !!!
     */
    public static EmailAuthenticationCode create(final String email, EmailAuthenticationCodeGenerator emailAuthenticationCodeGenerator) {
        String code = emailAuthenticationCodeGenerator.generate();
        final long AUTHENTICATION_CODE_EXPIRATION_SECOND = 3600L;
        return new EmailAuthenticationCode(null, email, code, AUTHENTICATION_CODE_EXPIRATION_SECOND);
    }

    /**
     * 사용시 주의!
     * 계정 객체 초기화 메서드로 아래 용도이외 에는 사용하지 않아야 합니다.
     * 데이터베이스에 Entity 혹은 Document 정보로 Account 객체를 초기화할 때 사용하는 메서드
     * EmailAuthenticationCode 객체의 모든 필드를 복사
     *
     * @param id:
     * @param email:
     * @param code:
     * @param expirationTime:
     * @return Account
     */
    public static EmailAuthenticationCode initialize(final String id, final String email, final String code, final long expirationTime) {
        return new EmailAuthenticationCode(id, email, code, expirationTime);
    }
}
