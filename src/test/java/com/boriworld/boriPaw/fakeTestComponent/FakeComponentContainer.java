package com.boriworld.boriPaw.fakeTestComponent;

import com.boriworld.boriPaw.userAccountService.command.application.UserAuthenticationService;
import com.boriworld.boriPaw.userAccountService.command.application.UserAccountManagementService;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.RefreshTokenRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.EmailCertificationCodeRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.event.UserAccountEventPublisher;
import com.boriworld.boriPaw.userAccountService.command.domain.service.*;
import com.boriworld.boriPaw.userAccountService.command.interfaces.controller.UserAccountManagementController;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidator;
import com.boriworld.boriPaw.common.validator.RequestObjectConstraintValidator;
import com.boriworld.boriPaw.fakeTestComponent.fakeComponents.*;
import com.boriworld.boriPaw.userAccountService.command.interfaces.controller.UserAuthenticationController;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;


public class FakeComponentContainer {
    public final UserAccountRepository userAccountRepository;
    public final UserAccountPasswordEncoder userAccountPasswordEncoder;
    public final UserAccountEventPublisher userAccountEventPublisher;
    public final EmailCertificationCodeGenerator emailCertificationCodeGenerator;
    public final EmailCertificationCodeRepository emailCertificationCodeRepository;
    public final RequestConstraintValidator requestConstraintValidator;
    public final UserAccountManagementService userAccountManagementService;
    public final UserAccountManagementController userAccountManagementController;
    public final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    public final Validator validator = validatorFactory.getValidator();
    public final UserAuthenticationService userAuthenticationService;
    public final UserAuthenticationController userAuthenticationController;
    public final RefreshTokenRepository refreshTokenRepository;
    public final AuthenticationTokenService authenticationTokenService;
    public final AuthenticationTokenPayloadEncoder authenticationTokenPayloadEncoder;
    public final UserAuthenticationContextManager userAuthenticationContextManager;

    public FakeComponentContainer() {
        this.userAccountRepository = new FakeUserAccountRepository();
        this.userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();
        this.userAccountEventPublisher = new FakeUserAccountEventPublisher();
        this.emailCertificationCodeGenerator = new FakeEmailCertificationCodeGenerator();
        this.emailCertificationCodeRepository = new FakeEmailCertificationCodeRepository();
        this.requestConstraintValidator = new RequestObjectConstraintValidator(validator);
        this.userAccountManagementService = new UserAccountManagementService(
                this.userAccountRepository,
                this.userAccountPasswordEncoder,
                this.userAccountEventPublisher,
                Set.of(new UserAccountCreateValidator(userAccountRepository))
        );
        refreshTokenRepository = new FakeRefreshTokenRepository();
        userAccountManagementController = new UserAccountManagementController(userAccountManagementService, requestConstraintValidator);
        authenticationTokenPayloadEncoder = new FakeTokenPayloadEncoder();
        userAuthenticationContextManager = new FakeUserAuthenticationContextManger();
        authenticationTokenService = new FakeAuthenticationTokenService(1000, 3000);
        userAuthenticationService = new UserAuthenticationService(
                userAccountRepository,
                userAccountPasswordEncoder,
                refreshTokenRepository,
                authenticationTokenService,
                authenticationTokenPayloadEncoder,
                userAuthenticationContextManager,
                userAccountEventPublisher);
        userAuthenticationController = new UserAuthenticationController(userAuthenticationService,requestConstraintValidator);
    }


}
