package com.boriworld.boriPaw.fakeTestComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;


public class FakeTokenPayloadEncoder implements AuthenticationTokenPayloadEncoder {

    private final String SUFFIX = "[ENCODED]";

    @Override
    public String encode(String payloadValue) {
        return SUFFIX + payloadValue;
    }

    @Override
    public String decode(String payloadValue) {
        return payloadValue.replace(SUFFIX, "");
    }

}
