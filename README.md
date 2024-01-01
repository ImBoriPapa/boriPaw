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
            ├── common
            │   ├── config
            │   ├── constant
            │   ├── exception
            │   ├── factory
            │   └── validator
            ├── followService
            │   ├── command
            │   │   ├── application
            │   │   ├── domain
            │   │   │   ├── event
            │   │   │   ├── exception
            │   │   │   ├── model
            │   │   │   ├── repository
            │   │   │   ├── service
            │   │   │   ├── usecase
            │   │   │   └── value
            │   │   ├── infrastructure
            │   │   │   ├── event
            │   │   │   ├── imports
            │   │   │   └── persistence
            │   │   └── interfaces
            │   │       ├── controller
            │   │       └── dto
            │   └── query
            │       ├── application
            │       ├── domain
            │       ├── infrastructure
            │       └── interfaces
            ├── supports
            │   ├── controller
            │   └── problem
            └── userAccountService
                ├── command
                │   ├── application
                │   │   └── dto
                │   ├── domain
                │   │   ├── dto
                │   │   ├── event
                │   │   ├── exception
                │   │   ├── model
                │   │   ├── repository
                │   │   ├── service
                │   │   ├── useCase
                │   │   └── value
                │   ├── handler
                │   ├── infrastructure
                │   │   ├── event
                │   │   ├── imports
                │   │   ├── persistence
                │   │   └── redis
                │   └── interfaces
                │       ├── controller
                │       ├── request
                │       └── response
                └── query
                    ├── application
                    ├── domain
                    │   ├── exception
                    │   ├── model
                    │   └── repository
                    ├── infrastructure
                    │   └── persistence
                    └── interfaces
                        ├── controller
                        └── response

70 directories
(base)  ~/Desktop/bori-world/boriPaw/src/main/java   main  tree   
.
└── com
    └── boriworld
        └── boriPaw
            ├── BoriPawApplication.java
            ├── TestData.java
            ├── common
            │   ├── config
            │   │   ├── AuthenticationTokenPayloadEncoderConfig.java
            │   │   ├── JpaConfig.java
            │   │   ├── RedisConfig.java
            │   │   ├── SecurityConfig.java
            │   │   └── ValidatorConfig.java
            │   ├── constant
            │   │   ├── ApiEndpoints.java
            │   │   ├── AuthenticationTokenHeaderNames.java
            │   │   ├── EncodeAlgorithm.java
            │   │   └── ProblemDefinition.java
            │   ├── exception
            │   │   ├── CustomRuntimeException.java
            │   │   └── GlobalExceptionHandler.java
            │   ├── factory
            │   │   └── ErrorResponseFactory.java
            │   └── validator
            │       ├── ConstraintValidationFailureException.java
            │       ├── RequestConstraintValidator.java
            │       └── RequestObjectConstraintValidator.java
            ├── followService
            │   ├── command
            │   │   ├── application
            │   │   │   └── FollowService.java
            │   │   ├── domain
            │   │   │   ├── event
            │   │   │   │   ├── FollowCreateEvent.java
            │   │   │   │   ├── FollowDeleteEvent.java
            │   │   │   │   ├── FollowEvent.java
            │   │   │   │   └── FollowEventPublisher.java
            │   │   │   ├── exception
            │   │   │   │   └── AlreadyFollowException.java
            │   │   │   ├── model
            │   │   │   │   ├── BlockFollower.java
            │   │   │   │   └── Follow.java
            │   │   │   ├── repository
            │   │   │   │   └── FollowRepository.java
            │   │   │   ├── service
            │   │   │   │   └── FollowValidator.java
            │   │   │   ├── usecase
            │   │   │   │   ├── FollowCreate.java
            │   │   │   │   └── FollowInitialize.java
            │   │   │   └── value
            │   │   │       ├── BlockFollowerId.java
            │   │   │       ├── FollowId.java
            │   │   │       ├── FollowStatus.java
            │   │   │       ├── Follower.java
            │   │   │       └── Following.java
            │   │   ├── infrastructure
            │   │   │   ├── event
            │   │   │   │   └── FollowEventApplicationPublisher.java
            │   │   │   ├── imports
            │   │   │   │   └── CreateFollowValidator.java
            │   │   │   └── persistence
            │   │   │       ├── FollowEntity.java
            │   │   │       ├── FollowJpaRepository.java
            │   │   │       └── FollowRepositoryImpl.java
            │   │   └── interfaces
            │   │       ├── controller
            │   │       │   └── FollowController.java
            │   │       └── dto
            │   │           └── FollowCreateRequest.java
            │   └── query
            │       ├── application
            │       │   ├── FollowQueryRepository.java
            │       │   └── FollowQueryService.java
            │       ├── domain
            │       │   ├── FollowerDetail.java
            │       │   ├── FollowerQuery.java
            │       │   ├── FollowerSlice.java
            │       │   ├── Followers.java
            │       │   ├── FollowingDetail.java
            │       │   ├── FollowingQuery.java
            │       │   └── FollowingSlice.java
            │       ├── infrastructure
            │       │   └── FollowQueryRepositoryJpaRepository.java
            │       └── interfaces
            │           └── FollowQueryController.java
            ├── supports
            │   ├── controller
            │   │   └── ProblemController.java
            │   └── problem
            └── userAccountService
                ├── command
                │   ├── application
                │   │   ├── ProfileManagementService.java
                │   │   ├── UserAccountManagementService.java
                │   │   ├── UserAuthenticationService.java
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
                │   │   ├── exception
                │   │   │   ├── AccessTokenDeniedException.java
                │   │   │   ├── AccessTokenExpiredException.java
                │   │   │   ├── AuthenticationTokenException.java
                │   │   │   ├── BlockUserAccountException.java
                │   │   │   ├── DuplicateEmailException.java
                │   │   │   ├── DuplicateUsernameException.java
                │   │   │   ├── LoginFailException.java
                │   │   │   ├── NotRegisteredEmailException.java
                │   │   │   ├── TokenReissueFailException.java
                │   │   │   ├── UnableToFindSupportedValidatorException.java
                │   │   │   └── UserAccountMismatchException.java
                │   │   ├── model
                │   │   │   ├── AccessToken.java
                │   │   │   ├── EmailCertificationCode.java
                │   │   │   ├── RefreshToken.java
                │   │   │   ├── UserAccount.java
                │   │   │   └── UserAccountLogin.java
                │   │   ├── repository
                │   │   │   ├── EmailCertificationCodeRepository.java
                │   │   │   ├── RefreshTokenRepository.java
                │   │   │   └── UserAccountRepository.java
                │   │   ├── service
                │   │   │   ├── AuthenticationTokenPayloadEncoder.java
                │   │   │   ├── AuthenticationTokenService.java
                │   │   │   ├── CertificationMailSender.java
                │   │   │   ├── EmailCertificationCodeGenerator.java
                │   │   │   ├── UserAccountCreateValidator.java
                │   │   │   ├── UserAccountPasswordEncoder.java
                │   │   │   ├── UserAccountValidator.java
                │   │   │   └── UserAuthenticationContextManager.java
                │   │   ├── useCase
                │   │   │   ├── AccessTokenCreate.java
                │   │   │   ├── AccessTokenReissue.java
                │   │   │   ├── RefreshTokenCreate.java
                │   │   │   ├── RefreshTokenInitialize.java
                │   │   │   ├── SendCertificationEmail.java
                │   │   │   ├── UserAccountCreate.java
                │   │   │   ├── UserAccountInitialize.java
                │   │   │   └── UserAccountPasswordChange.java
                │   │   └── value
                │   │       ├── AccountStatus.java
                │   │       ├── AuthenticationTokenStatus.java
                │   │       ├── AuthenticationTokenType.java
                │   │       ├── Authority.java
                │   │       ├── NotFoundAuthorityFromStringException.java
                │   │       ├── PasswordStatus.java
                │   │       ├── RefreshTokenId.java
                │   │       ├── UserAccountId.java
                │   │       └── UserProfile.java
                │   ├── handler
                │   │   ├── AuthenticationTokenExceptionHandler.java
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
                │   │   │   ├── SpringSecurityUserAuthenticationContextManager.java
                │   │   │   └── UserAccountPasswordEncoderImpl.java
                │   │   ├── persistence
                │   │   │   ├── UserAccountEntity.java
                │   │   │   ├── UserAccountJpaRepository.java
                │   │   │   ├── UserAccountRepositoryImpl.java
                │   │   │   └── UserProfileValue.java
                │   │   └── redis
                │   │       ├── InMemoryEmailCertificationCodeRepository.java
                │   │       ├── RefreshTokenRedisTemplateRepository.java
                │   │       └── RefreshTokenStrings.java
                │   └── interfaces
                │       ├── controller
                │       │   ├── UserAccountManagementController.java
                │       │   └── UserAuthenticationController.java
                │       ├── request
                │       │   ├── EmailCertificationRequest.java
                │       │   ├── LoginRequest.java
                │       │   └── UserAccountCreateRequest.java
                │       └── response
                │           ├── EmailCertificationResponse.java
                │           ├── LoginResponse.java
                │           ├── ReissueResponse.java
                │           └── UserAccountCreateResponse.java
                └── query
                    ├── application
                    │   ├── UserAccountQueryService.java
                    │   └── UserAuthenticationQueryService.java
                    ├── domain
                    │   ├── exception
                    │   │   └── ResourceNotFoundException.java
                    │   ├── model
                    │   │   ├── UserInformation.java
                    │   │   └── UserProfileDetail.java
                    │   └── repository
                    │       ├── UserAccountQueryRepository.java
                    │       └── UserAuthenticationQueryRepository.java
                    ├── infrastructure
                    │   └── persistence
                    │       ├── QueryDslUserAccountQueryRepository.java
                    │       └── UserAuthenticationQueryDslRepository.java
                    └── interfaces
                        ├── controller
                        │   ├── UserAccountQueryController.java
                        │   └── UserAuthenticationQueryController.java
                        └── response
                            ├── UserInformationResponse.java
                            └── UserProfileDetailResponse.java


```