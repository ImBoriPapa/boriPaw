package com.boriworld.boriPaw.testComponent;

import com.boriworld.boriPaw.userAccountService.command.application.AccountManagementService;
import com.boriworld.boriPaw.userAccountService.command.application.AccountManagementServiceImpl;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.EmailCertificationCodeRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountEventPublisher;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountValidator;
import com.boriworld.boriPaw.userAccountService.command.domain.service.EmailCertificationCodeGenerator;
import com.boriworld.boriPaw.userAccountService.command.interfaces.AccountManagementController;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidator;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidatorImpl;
import com.boriworld.boriPaw.testComponent.fakeComponents.*;
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
    public final AccountManagementService accountManagementService;
    public final AccountManagementController accountManagementController;
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    public TestComponentContainer() {
        this.userAccountRepository = new FakeUserAccountRepository();
        this.userAccountValidator = new FakeUserAccountValidator(userAccountRepository);
        this.userAccountPasswordEncoder = new FakeUserAccountPasswordEncoder();
        this.userAccountEventPublisher = new FakeUserAccountEventPublisher();
        this.emailCertificationCodeGenerator = new FakeEmailCertificationCodeGenerator();
        this.emailCertificationCodeRepository = new FakeEmailCertificationCodeRepository();
        this.requestConstraintValidator = new RequestConstraintValidatorImpl(validator);
        this.accountManagementService = new AccountManagementServiceImpl(
                this.userAccountRepository,
                this.userAccountPasswordEncoder,
                this.userAccountEventPublisher,
                this.requestConstraintValidator,
                this.userAccountValidator
        );
        accountManagementController = new AccountManagementController(accountManagementService);
    }


}
