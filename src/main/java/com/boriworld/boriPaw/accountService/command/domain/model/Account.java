package com.boriworld.boriPaw.accountService.command.domain.model;

import com.boriworld.boriPaw.accountService.command.application.LoginFailException;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountPasswordEncoder;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountInitialize;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountStatus;
import com.boriworld.boriPaw.accountService.command.domain.value.Authority;
import com.boriworld.boriPaw.accountService.command.domain.value.PasswordStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
public final class Account {
    private final AccountId accountId;
    private final String email;
    private final String accountName;
    private final String password;
    private final String nickname;
    private final String profileImage;
    private final AccountStatus accountStatus;
    private final PasswordStatus passwordStatus;
    private final Authority authority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime lastLoginAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Account(AccountId accountId, String email, String accountName, String password, String nickname, String profileImage, AccountStatus accountStatus, PasswordStatus passwordStatus, Authority authority, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastLoginAt) {
        this.accountId = accountId;
        this.email = email;
        this.accountName = accountName;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.accountStatus = accountStatus;
        this.passwordStatus = passwordStatus;
        this.authority = authority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * role: Account 객체 생성 정적 팩토리 메서드
     * responsibility: 계정 생성 요청에 따라 Account 계정 생성 정책에 따라 초기값을 설정하고 객체를 생성
     * 초기화 정보:
     * email -> 평문 저장
     * password -> AccountPasswordEncoder 를 사용해서 암호화
     * nickname -> 평문 저장
     * AccountStatus -> 이메일 인증이 완료 전까지 PENDING
     * PasswordStatus -> STEADY(정상 상태)
     * Authority -> 최초 가입시 USER 권한
     * createdAt -> 계정 생성 시간
     * updatedAt -> 계정 업데이트 시간
     *
     * @param accountCreate:          Account 생성을 위한 정보 전달 DTO
     * @param accountPasswordEncoder: 계정 비밀번호 생성기
     * @return Account
     */
    public static Account from(AccountCreate accountCreate, AccountPasswordEncoder accountPasswordEncoder) {
        Objects.requireNonNull(accountCreate, "createAccount() 메서드에 매개 변수 'accountCreate' 는 NULL 이 될수 없습니다..");
        Objects.requireNonNull(accountPasswordEncoder, "createAccount() 메서드에 매개 변수 'accountPasswordEncoder' 는 NULL 이 될수 없습니다.");

        return Account.builder()
                .email(accountCreate.email())
                .accountName(accountCreate.accountName())
                .password(accountPasswordEncoder.encode(accountCreate.password()))
                .nickname(accountCreate.nickname())
                .accountStatus(AccountStatus.PENDING)
                .passwordStatus(PasswordStatus.STEADY)
                .authority(Authority.USER)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * 사용시 주의!
     * 계정 객체 초기화 메서드로 아래 용도이외 에는 사용하지 않아야 합니다.
     * 데이터베이스에 Entity 혹은 Document 정보로 Account 객체를 초기화할 때 사용하는 메서드
     * Account 객체의 모든 필드를 복사
     *
     * @param initialize
     * @return Account
     */
    public static Account initialize(AccountInitialize initialize) {

        return Account.builder()
                .accountId(initialize.accountId())
                .email(initialize.email())
                .accountName(initialize.accountName())
                .password(initialize.password())
                .nickname(initialize.nickname())
                .profileImage(initialize.profileImage())
                .accountStatus(initialize.accountStatus())
                .passwordStatus(initialize.passwordStatus())
                .authority(initialize.authority())
                .createdAt(initialize.createdAt())
                .updatedAt(initialize.updatedAt())
                .build();
    }

    public Account login(final String password, AccountPasswordEncoder accountPasswordEncoder) {
        preventLoginByAccountStatus(this.accountStatus);
        checkPassword(password, accountPasswordEncoder);
        return Account.builder()
                .accountId(this.accountId)
                .email(this.email)
                .accountName(this.accountName)
                .password(this.password)
                .nickname(this.nickname)
                .profileImage(this.profileImage)
                .accountStatus(this.accountStatus)
                .passwordStatus(this.passwordStatus)
                .authority(this.authority)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .lastLoginAt(LocalDateTime.now())
                .build();
    }

    private void checkPassword(String password, AccountPasswordEncoder accountPasswordEncoder) {
        if (accountPasswordEncoder.isMatch(this.password, password)) {
            throw new LoginFailException("잘못된 이메일 혹은 잘못된 비밀번호입니다.");
        }
    }

    public void preventLoginByAccountStatus(AccountStatus accountStatus) {
        if (accountStatus != AccountStatus.ACTIVE) {
            throw new LoginFailException(accountStatus.getErrorMessage());
        }
    }

}
