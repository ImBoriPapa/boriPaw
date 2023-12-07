package com.boriworld.boriPaw.userAccountService.command.domain.value;

import java.util.Arrays;

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

    public static AccountStatus fromString(String string) {
        return Arrays.stream(AccountStatus.values())
                .filter(accountStatus -> accountStatus.name().equals(string))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없다."));
    }

}
