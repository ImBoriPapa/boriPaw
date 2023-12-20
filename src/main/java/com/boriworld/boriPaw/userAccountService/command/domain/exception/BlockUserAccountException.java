package com.boriworld.boriPaw.userAccountService.command.domain.exception;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import com.boriworld.boriPaw.common.exception.CustomRuntimeException;

public final class BlockUserAccountException extends CustomRuntimeException {
    private BlockUserAccountException(String message, ProblemDefinition problemDefinition) {
        super(message, problemDefinition);
    }

    public static BlockUserAccountException forMessage(final String message) {
        return new BlockUserAccountException(message, ProblemDefinition.BLOCK_USER_ACCOUNT);
    }

}
