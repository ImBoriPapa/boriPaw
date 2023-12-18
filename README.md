# BoriPaw Project

-----------------------------------------

## Social Network Service API Server

-----------------------------------------
## Test Ground
### Test Pyramid 적용
#### Large Test(E2E Test)
- 비율 5%
- Front 와 직접 데이터 요청 및 응답 테스트

#### Medium Test(Integration Test) 
- 비율 15% 
- 시스템과 시스템의 연결 및 동작 테스트
  - ex) application <-> MySQL, application <-> Redis
  - TestContainer 을 사용해 멱등성 보장 
- 테스트가 느림 개선 방안을 찾아야함

#### Small Test(Unit Test)
- 비율 80%
- 메서드 단위 테스트 작성
- Application Code Level 테스트 작성
- 외부 의존성 Fake 객체로 생성해서 테스트

## 프로젝트 구조 2022/12/19
```
.
└── com
    └── boriworld
        └── boriPaw
            ├── BoriPawApplication.java
            ├── common
            │   ├── config
            │   │   ├── AuthenticationTokenPayloadEncoderConfig.java
            │   │   ├── JpaConfig.java
            │   │   ├── SecurityConfig.java
            │   │   └── ValidatorConfig.java
            │   ├── constant
            │   │   ├── ApiEndpoints.java
            │   │   ├── AuthenticationTokenHeaders.java
            │   │   ├── EncodeAlgorithm.java
            │   │   └── ProblemDefinition.java
            │   ├── exception
            │   │   ├── CustomRuntimeException.java
            │   │   └── GlobalExceptionHandler.java
            │   └── validator
            │       ├── CustomValidationFailException.java
            │       ├── RequestConstraintValidator.java
            │       └── RequestConstraintValidatorImpl.java
            ├── supports
            │   ├── controller
            │   │   └── ProblemController.java
            │   └── problem
            └── userAccountService
                ├── command
                │   ├── application
                │   │   ├── LoginFailException.java
                │   │   ├── ProfileManagementService.java
                │   │   ├── UnableToFindSupportedValidatorException.java
                │   │   ├── UserAccountAuthenticationService.java
                │   │   ├── UserAccountManagementService.java
                │   │   ├── UserAccountManagementServiceImpl.java
                │   │   └── dto
                │   │       ├── EmailCertification.java
                │   │       └── LoginProcess.java
                │   ├── domain
                │   │   ├── dto
                │   │   │   ├── AuthenticationToken.java
                │   │   │   ├── AuthenticationTokenCredentials.java
                │   │   │   └── UserAccountPrincipal.java
                │   │   ├── event
                │   │   │   ├── AccountCreateEvent.java
                │   │   │   ├── AccountEvent.java
                │   │   │   └── UserAccountEventPublisher.java
                │   │   ├── model
                │   │   │   ├── AccessToken.java
                │   │   │   ├── EmailCertificationCode.java
                │   │   │   ├── RefreshToken.java
                │   │   │   └── UserAccount.java
                │   │   ├── repository
                │   │   │   ├── EmailCertificationCodeRepository.java
                │   │   │   ├── RefreshTokenRepository.java
                │   │   │   └── UserAccountRepository.java
                │   │   ├── service
                │   │   │   ├── AuthenticationTokenPayloadEncoder.java
                │   │   │   ├── AuthenticationTokenService.java
                │   │   │   ├── CertificationMailSender.java
                │   │   │   ├── EmailCertificationCodeGenerator.java
                │   │   │   ├── SecurityContextManager.java
                │   │   │   ├── UserAccountCreateValidator.java
                │   │   │   ├── UserAccountPasswordEncoder.java
                │   │   │   └── UserAccountValidator.java
                │   │   ├── useCase
                │   │   │   ├── AccessTokenCreate.java
                │   │   │   ├── AccessTokenReissue.java
                │   │   │   ├── RefreshTokenCreate.java
                │   │   │   ├── SendCertificationEmail.java
                │   │   │   ├── UserAccountCreate.java
                │   │   │   ├── UserAccountInitialize.java
                │   │   │   └── UserAccountPasswordChange.java
                │   │   └── value
                │   │       ├── AccountStatus.java
                │   │       ├── AuthenticationTokenStatus.java
                │   │       ├── AuthenticationTokenType.java
                │   │       ├── Authority.java
                │   │       ├── PasswordStatus.java
                │   │       ├── RefreshTokenId.java
                │   │       ├── UserAccountId.java
                │   │       └── UserProfile.java
                │   ├── exception
                │   │   ├── AccessTokenDeniedException.java
                │   │   ├── AccessTokenExpiredException.java
                │   │   ├── AuthenticationTokenException.java
                │   │   ├── BlockUserAccountException.java
                │   │   ├── DuplicateEmailException.java
                │   │   ├── DuplicateUsernameException.java
                │   │   └── UserAccountMismatchException.java
                │   ├── handler
                │   │   ├── JwtAccessDeniedHandler.java
                │   │   ├── JwtAuthenticationEntryPoint.java
                │   │   └── JwtAuthenticationFilter.java
                │   ├── infrastructure
                │   │   ├── event
                │   │   │   └── InMemoryEventPublisherUser.java
                │   │   ├── imports
                │   │   │   ├── AuthenticationTokenPayloadEncoderException.java
                │   │   │   ├── CertificationMailSenderImpl.java
                │   │   │   ├── CipherAuthenticationTokenPayloadEncoder.java
                │   │   │   ├── EmailCertificationCodeGeneratorImpl.java
                │   │   │   ├── JwtAuthenticationTokenService.java
                │   │   │   ├── SecurityContextManagerImpl.java
                │   │   │   └── UserAccountPasswordEncoderImpl.java
                │   │   └── persistence
                │   │       ├── AccountJpaRepository.java
                │   │       ├── InMemoryEmailCertificationCodeRepository.java
                │   │       ├── RefreshTokenRepositoryImpl.java
                │   │       ├── UserAccountEntity.java
                │   │       ├── UserAccountRepositoryImpl.java
                │   │       └── UserProfileValue.java
                │   └── interfaces
                │       ├── AccountManagementController.java
                │       ├── EmailCertificationResponse.java
                │       ├── ReissueResponse.java
                │       ├── TokenReissueFailException.java
                │       ├── UserAccountAuthenticationController.java
                │       ├── request
                │       │   ├── EmailCertificationRequest.java
                │       │   ├── LoginRequest.java
                │       │   └── UserAccountCreateRequest.java
                │       └── response
                │           ├── LoginResponse.java
                │           └── UserAccountCreateResponse.java
                └── query
                    ├── application
                    │   ├── ResourceNotFoundException.java
                    │   └── UserAccountQueryService.java
                    ├── domain
                    │   ├── model
                    │   │   └── UserInformation.java
                    │   └── repository
                    │       └── UserAccountQueryRepository.java
                    ├── infrastructure
                    │   └── persistence
                    │       └── QueryDslUserAccountQueryRepository.java
                    └── interfaces
                        ├── UserAccountQueryController.java
                        └── response
                            └── UserInformationResponse.java

```