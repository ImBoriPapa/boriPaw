package com.boriworld.boriPaw.userAccountService.command.domain.value;

public enum AccountStatus {
    PENDING("이메일 인증이 필요합니다."),
    ACTIVE("계정 상태 정상"),
    BLOCK("사용할 수 없는 계정");
    private final String errorMessage;
    AccountStatus(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
