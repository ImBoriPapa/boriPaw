package com.boriworld.boriPaw.testComponent;

import com.boriworld.boriPaw.accountService.command.application.AccountManagementService;
import com.boriworld.boriPaw.accountService.command.application.AccountManagementServiceImpl;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.accountService.command.domain.repository.EmailCertificationCodeRepository;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountEventPublisher;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountPasswordEncoder;
import com.boriworld.boriPaw.accountService.command.domain.service.AccountValidator;
import com.boriworld.boriPaw.accountService.command.domain.service.EmailCertificationCodeGenerator;
import com.boriworld.boriPaw.accountService.command.interfaces.AccountManagementController;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidator;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidatorImpl;
import com.boriworld.boriPaw.testComponent.fakeComponents.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


public class TestComponentContainer {
    public final AccountRepository accountRepository;
    public final AccountValidator accountValidator;
    public final AccountPasswordEncoder accountPasswordEncoder;
    public final AccountEventPublisher accountEventPublisher;
    public final EmailCertificationCodeGenerator emailCertificationCodeGenerator;
    public final EmailCertificationCodeRepository emailCertificationCodeRepository;
    public final RequestConstraintValidator requestConstraintValidator;
    public final AccountManagementService accountManagementService;
    public final AccountManagementController accountManagementController;
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    public TestComponentContainer() {
        this.accountRepository = new FakeAccountRepository();
        this.accountValidator = new FakeAccountValidator(accountRepository);
        this.accountPasswordEncoder = new FakeAccountPasswordEncoder();
        this.accountEventPublisher = new FakeAccountEventPublisher();
        this.emailCertificationCodeGenerator = new FakeEmailCertificationCodeGenerator();
        this.emailCertificationCodeRepository = new FakeEmailCertificationCodeRepository();
        this.requestConstraintValidator = new RequestConstraintValidatorImpl(validator);
        this.accountManagementService = new AccountManagementServiceImpl(
                this.accountRepository,
                this.accountPasswordEncoder,
                this.accountEventPublisher,
                this.requestConstraintValidator,
                this.accountValidator
        );
        accountManagementController = new AccountManagementController(accountManagementService);
    }


}
