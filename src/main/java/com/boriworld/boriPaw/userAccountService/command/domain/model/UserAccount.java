package com.boriworld.boriPaw.userAccountService.command.domain.model;

import com.boriworld.boriPaw.userAccountService.command.domain.exception.LoginFailException;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountInitialize;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountLogin;
import com.boriworld.boriPaw.userAccountService.command.domain.value.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Slf4j
public final class UserAccount {
    private final UserAccountId userAccountId;
    private final String email;
    private final String username;
    private final String password;
    private final AccountStatus accountStatus;
    private final PasswordStatus passwordStatus;
    private final Authority authority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime lastLoginAt;

    @Builder(access = AccessLevel.PRIVATE)
    private UserAccount(UserAccountId userAccountId, String email, String username, String password, AccountStatus accountStatus, PasswordStatus passwordStatus, Authority authority, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastLoginAt) {
        this.userAccountId = userAccountId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.accountStatus = accountStatus;
        this.passwordStatus = passwordStatus;
        this.authority = authority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLoginAt = lastLoginAt;
    }

    /*
     * role: Account 객체 생성 정적 팩토리 메서드
     * responsibility: 계정 생성 요청에 따라 Account 계정 생성 정책에 따라 초기값을 설정하고 객체를 생성
     * 초기화 정보:
     * email -> 평문 저장
     * password -> AccountPasswordEncoder 를 사용해서 암호화
     * nickname -> 평문 저장
     * AccountStatus -> 이메일 인증이 완료 전까지 PENDING -> 메일링 서비스 개발 전까지 ACTIVE
     * PasswordStatus -> STEADY(정상 상태)
     * Authority -> 최초 가입시 USER 권한
     * createdAt -> 계정 생성 시간
     * updatedAt -> 계정 업데이트 시간
     *
     * @param userAccountCreate:          Account 생성을 위한 정보 전달 DTO
     * @param userAccountPasswordEncoder: 계정 비밀번호 생성기
     * @return Account
     */
    public static UserAccount from(UserAccountCreate userAccountCreate, UserAccountPasswordEncoder userAccountPasswordEncoder) {
        Objects.requireNonNull(userAccountCreate, "createAccount() 메서드에 매개 변수 'accountCreate' 는 NULL 이 될수 없습니다..");
        Objects.requireNonNull(userAccountPasswordEncoder, "createAccount() 메서드에 매개 변수 'accountPasswordEncoder' 는 NULL 이 될수 없습니다.");
        log.info("create user account from UserAccountCreate");

        String username = userAccountCreate.email().split("@")[0];

        return UserAccount.builder()
                .email(userAccountCreate.email())
                .username(username)
                .password(userAccountPasswordEncoder.encode(userAccountCreate.password()))
                .accountStatus(AccountStatus.ACTIVE)
                .passwordStatus(PasswordStatus.STEADY)
                .authority(Authority.USER)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * 사용시 주의!
     * 계정 객체 초기화 메서드로 아래 용도외 에는 사용하지 않아야 합니다.
     * 데이터베이스에 Entity 혹은 Document 정보로 Account 객체를 초기화할 때 사용하는 메서드
     * Account 객체의 모든 필드를 복사
     *
     * @return Account
     */
    public static UserAccount initialize(UserAccountInitialize initialize) {
        return UserAccount.builder()
                .userAccountId(initialize.getUserAccountId())
                .email(initialize.getEmail())
                .username(initialize.getUsername())
                .password(initialize.getPassword())
                .accountStatus(initialize.getAccountStatus())
                .passwordStatus(initialize.getPasswordStatus())
                .authority(initialize.getAuthority())
                .createdAt(initialize.getCreatedAt())
                .updatedAt(initialize.getUpdatedAt())
                .lastLoginAt(initialize.getLastLoginAt())
                .build();
    }

    public UserAccount login(UserAccountLogin userAccountLogin, UserAccountPasswordEncoder userAccountPasswordEncoder) {
        preventLoginByAccountStatus(this.accountStatus);
        checkPassword(userAccountLogin.password(), userAccountPasswordEncoder);
        return UserAccount.builder()
                .userAccountId(this.userAccountId)
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .accountStatus(this.accountStatus)
                .passwordStatus(this.passwordStatus)
                .authority(this.authority)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .lastLoginAt(LocalDateTime.now())
                .build();
    }

    private void checkPassword(String password, UserAccountPasswordEncoder userAccountPasswordEncoder) {
        if (!userAccountPasswordEncoder.isMatch(password, this.password)) {
            throw LoginFailException.forMessage("잘못된 이메일 혹은 잘못된 비밀번호입니다.");
        }
    }

    private void preventLoginByAccountStatus(AccountStatus accountStatus) {
        if (accountStatus != AccountStatus.ACTIVE) {
            throw LoginFailException.forMessage(accountStatus.getErrorMessage());
        }
    }

    public UserAccount changeUpdatedAt() {
        return UserAccount.builder()
                .userAccountId(this.userAccountId)
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .accountStatus(this.accountStatus)
                .passwordStatus(this.passwordStatus)
                .authority(this.authority)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .lastLoginAt(LocalDateTime.now())
                .build();
    }
}
