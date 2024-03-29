package com.boriworld.boriPaw.common.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProblemDefinition {
    NOT_FOUND_PROBLEM("not-found-problem", "문제 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "문제 정보를 찾을 수 없습니다. 개발자에게 문의해주세요"),
    DUPLICATE_EMAIL("duplicate-email", "Duplicate Email", HttpStatus.CONFLICT, "이미 사용중인 이메일로 계정생성 요청에 발생하는 문제입니다."),
    DUPLICATE_USERNAME("duplicate-username", "Duplicate Username", HttpStatus.CONFLICT, "이미 시용중인 Username 으로 계정 생성 요청시 발생하는 문제입니다."),
    USER_ACCOUNT_MISMATCH("userAccount-mismatch", "Mismatch UserAccount", HttpStatus.BAD_REQUEST, ""),
    BLOCK_USER_ACCOUNT("block-userAccount", "Block UserAccount", HttpStatus.UNAUTHORIZED, ""),
    CONSTRAINT_VALIDATION_FAIL("constraint-validation-fail", "Constraint validation fail", HttpStatus.BAD_REQUEST, "요청에 유효성 검증에 실패했을 경우 입니다. 요청 데이터를 확인해주세요"),
    UNABLE_FIND_SUPPORTABLE_VALIDATOR("", "", HttpStatus.BAD_REQUEST, ""),
    NOT_FOUND_RESOURCE("", "", HttpStatus.NOT_FOUND, ""),
    LOGIN_FAIL("", "", HttpStatus.UNAUTHORIZED, ""),
    NOT_REGISTERED_EMAIL("not-registered-email", "not registered", HttpStatus.NOT_FOUND, ""),
    NOT_FOUND_AUTHORITY_FROM_STRING("here ", "here", HttpStatus.BAD_REQUEST, ""),
    DENIED_TOKEN("denied-token", "token-denied", HttpStatus.BAD_REQUEST, "거절된 토큰입니다."),
    EXPIRED_TOKEN("expired-token", "token-expired", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    ERROR_TOKEN("error-token", "token-error", HttpStatus.FORBIDDEN, "잘못된 토큰입니다."),
    ALREADY_FOLLOW("type", "title", HttpStatus.BAD_REQUEST, ""),
    NO_RESOURCE_UPDATE_PRIVILEGES("type", "No Resource update privileges", HttpStatus.FORBIDDEN, "해당 리소스에 수정 권한 없음"),
    NOT_FOUND_MULTIPART_FILE_NAME("type", "Not found Multipart name", HttpStatus.BAD_REQUEST, "요청에 필요한 Multipart file name 을 찾을 수 없을 경우");
    private final String type;
    private final String title;
    private final HttpStatus status;
    private final String explanation;

    ProblemDefinition(String type, String title, HttpStatus status, String explanation) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.explanation = explanation;
    }
}
