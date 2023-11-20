package com.boriworld.boriPaw.accountService.command.interfaces;

import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.common.validator.RequestObjectValidator;

public class FakeRequestObjectValidator implements RequestObjectValidator<AccountCreate> {
    @Override
    public void validate(AccountCreate accountCreate) {

    }
}
