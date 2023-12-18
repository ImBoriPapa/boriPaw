package com.boriworld.boriPaw.userAccountService.command.domain.useCase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserAccountCreateValidatorSmallTest {

    @Test
    void whenEmailIsNullThrowException() throws Exception {
        //given
        final String email = null;
        final String username = "";
        final String password = "";
        final String nickname = "";
        //when

        //then
        assertThatThrownBy(() -> new UserAccountCreate(email, username, password, nickname))
                .isInstanceOf(Exception.class);
    }

}