package com.boriworld.boriPaw.testContanier;

import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.common.validator.CustomValidationFailException;
import com.boriworld.boriPaw.common.validator.RequestConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class FakeRequestConstraintValidator implements RequestConstraintValidator<AccountCreate> {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    @Override
    public void validate(AccountCreate accountCreate) {
        Set<String> strings = validator.validate(accountCreate).stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        if (!strings.isEmpty()) {
            throw new CustomValidationFailException(String.join("\n",strings));
        }

    }
}
