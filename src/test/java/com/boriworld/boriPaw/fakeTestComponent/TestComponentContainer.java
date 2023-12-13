package com.boriworld.boriPaw.fakeTestComponent;

import com.boriworld.boriPaw.userAccountService.command.application.UserAccountAuthenticationService;
import com.boriworld.boriPaw.userAccountService.command.application.UserAccountManagementService;
import com.boriworld.boriPaw.userAccountService.command.application.UserAccountManagementServiceImpl;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.RefreshTokenRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.EmailCertificationCodeRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.event.UserAccountEventPublisher;
import com.boriworld.boriPaw.userAccountService.command.domain.service.*;
import com.boriworld.boriPaw.userAccountService.command.interfaces.AccountManagementController;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidator;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidatorImpl;
import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.*;
import com.boriworld.boriPaw.userAccountService.command.interfaces.UserAccountAuthenticationController;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


public class TestComponentContainer {
    public final UserAccountRepository userAccountRepository;
    public final UserAccountValidator userAccountValidator;
    public final UserAccountPasswordEncoder userAccountPasswordEncoder;
    public final UserAccountEventPublisher userAccountEventPublisher;
    public final EmailCertificationCodeGenerator emailCertificationCodeGenerator;
    public final EmailCertificationCodeRepository emailCertificationCodeRepository;
    public final RequestConstraintValidator requestConstraintValidator;
    public final UserAccountManagementService userAccountManagementService;
    public final AccountManagementController accountManagementController;
    public final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    public final Validator validator = validatorFactory.getValidator();
    public final UserAccountAuthenticationService userAccountAuthenticationService;
    public final UserAccountAuthenticationController userAccountAuthenticationController;
    public final RefreshTokenRepository refreshTokenRepository;
    public final AuthenticationTokenService authenticationTokenService;
    public final AuthenticationTokenPayloadEncoder authenticationTokenPayloadEncoder;
    public final SecurityContextManager securityContextManager;

    public TestComponentContainer() {
        this.userAccountRepository = new FakeUserAccountRepository();
        this.userAccountValidator = new FakeUserAccountValidator(userAccountRepository);
        this.userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();
        this.userAccountEventPublisher = new FakeUserAccountEventPublisher();
        this.emailCertificationCodeGenerator = new FakeEmailCertificationCodeGenerator();
        this.emailCertificationCodeRepository = new FakeEmailCertificationCodeRepository();
        this.requestConstraintValidator = new RequestConstraintValidatorImpl(validator);
        this.userAccountManagementService = new UserAccountManagementServiceImpl(
                this.userAccountRepository,
                this.userAccountPasswordEncoder,
                this.userAccountEventPublisher,
                this.requestConstraintValidator,
                this.userAccountValidator
        );
        refreshTokenRepository = new FakeRefreshTokenRepository();
        accountManagementController = new AccountManagementController(userAccountManagementService);
        authenticationTokenPayloadEncoder = new FakeTokenPayloadEncoder();
        securityContextManager = new FakeSecurityContextManger();
        authenticationTokenService = new FakeAuthenticationTokenService(1000, 3000);
        userAccountAuthenticationService = new UserAccountAuthenticationService(
                userAccountRepository,
                userAccountPasswordEncoder,
                refreshTokenRepository,
                authenticationTokenService,
                authenticationTokenPayloadEncoder,
                securityContextManager,
                userAccountEventPublisher);
        userAccountAuthenticationController = new UserAccountAuthenticationController(userAccountAuthenticationService);
    }


}
