package com.boriworld.boriPaw.accountService.command.domain;

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
     * @param email: user email
     * @param emailAuthenticationCodeGenerator: 인증 코드 생성기
     * @return EmailAuthenticationCode: !!!
     */
    public static EmailAuthenticationCode create(final String email, EmailAuthenticationCodeGenerator emailAuthenticationCodeGenerator) {
        String code = emailAuthenticationCodeGenerator.generate();
        final long AUTHENTICATION_CODE_EXPIRATION_SECOND = 3600L;
        return new EmailAuthenticationCode(null, email, code, AUTHENTICATION_CODE_EXPIRATION_SECOND);
    }

    public String getCode() {
        return this.code;
    }
}
