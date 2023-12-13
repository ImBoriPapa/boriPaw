package com.boriworld.boriPaw.userAccountService.command.infrastructure.imports;

import com.boriworld.boriPaw.common.constant.EncodeAlgorithm;
import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CipherAuthenticationTokenPayloadEncoderConfigSmallTest{
    @Test
    @DisplayName("EncodeAlgorithm null: NullPointException")
    void test1() throws Exception {
        //given
        final String secretKey = "";
        final String iv = "";
        final EncodeAlgorithm encodeAlgorithm = null;
        //when
        assertThatThrownBy(() ->
                CipherAuthenticationTokenPayloadEncoder
                        .builder()
                        .secretKey(secretKey)
                        .initializationVector(iv)
                        .encodeAlgorithm(encodeAlgorithm)
                        .build()
        ).isInstanceOf(NullPointerException.class);
        //then
    }

    @Test
    @DisplayName("secretKey null: NullPointException")
    void test2() throws Exception {
        //given
        final String secretKey = null;
        final String initializationVector = "iv";
        final EncodeAlgorithm encodeAlgorithm = EncodeAlgorithm.AES;
        //when
        assertThatThrownBy(() ->
                CipherAuthenticationTokenPayloadEncoder
                        .builder()
                        .secretKey(secretKey)
                        .initializationVector(initializationVector)
                        .encodeAlgorithm(encodeAlgorithm)
                        .build()
        ).isInstanceOf(NullPointerException.class);
        //then
    }

    @Test
    @DisplayName("initializationVector null: NullPointException")
    void test3() throws Exception {
        //given
        final String secretKey = null;
        final String initializationVector = "iv";
        final EncodeAlgorithm encodeAlgorithm = EncodeAlgorithm.AES;
        //when
        assertThatThrownBy(() ->
                CipherAuthenticationTokenPayloadEncoder
                        .builder()
                        .secretKey(secretKey)
                        .initializationVector(initializationVector)
                        .encodeAlgorithm(encodeAlgorithm)
                        .build()
        ).isInstanceOf(NullPointerException.class);
        //then
    }

    @Test
    @DisplayName("secretKey less then 32: AuthenticationTokenPayloadEncoderException")
    void test4() throws Exception {
        //given
        final String secretKey = "tooShot";
        final String initializationVector = "iv";
        final EncodeAlgorithm encodeAlgorithm = EncodeAlgorithm.AES;
        //when
        assertThatThrownBy(() ->
                CipherAuthenticationTokenPayloadEncoder
                        .builder()
                        .secretKey(secretKey)
                        .initializationVector(initializationVector)
                        .encodeAlgorithm(encodeAlgorithm)
                        .build()
        ).isInstanceOf(AuthenticationTokenPayloadEncoderException.class);
        //then
    }

    @Test
    @DisplayName("initializationVector less then 32: AuthenticationTokenPayloadEncoderException")
    void test5() throws Exception {
        //given
        final String secretKey = "12345678901234567890123456789012";
        final String initializationVector = "iv";
        final EncodeAlgorithm encodeAlgorithm = EncodeAlgorithm.AES;
        //when
        assertThatThrownBy(() ->
                CipherAuthenticationTokenPayloadEncoder
                        .builder()
                        .secretKey(secretKey)
                        .initializationVector(initializationVector)
                        .encodeAlgorithm(encodeAlgorithm)
                        .build()
        ).isInstanceOf(AuthenticationTokenPayloadEncoderException.class);
        //then
    }

    @Test
    @DisplayName("CipherAuthenticationTokenPayloadEncoder.builder() 로 AuthenticationTokenPayloadEncoder 를 만들 수 있다.")
    void test6() throws Exception {
        //given
        final String secretKey = "12345678901234567890123456789012";
        final String initializationVector = "12345678901234567890123456789012";
        final EncodeAlgorithm encodeAlgorithm = EncodeAlgorithm.AES;
        //when
        AuthenticationTokenPayloadEncoder payloadEncoder = CipherAuthenticationTokenPayloadEncoder
                .builder()
                .secretKey(secretKey)
                .initializationVector(initializationVector)
                .encodeAlgorithm(encodeAlgorithm)
                .build();
        //then
        assertThat(payloadEncoder).isNotNull();
    }

    @Test
    @DisplayName("encode test")
    void encodeTest() throws Exception {
        //given
        final String secreteKey = "12345678901234567890123456789012";
        final String initializationVector = "12345678901234567890123456789012";
        final EncodeAlgorithm encodeAlgorithm = EncodeAlgorithm.AES;
        final String payload = "IS_SECRET";
        AuthenticationTokenPayloadEncoder payloadEncoder = CipherAuthenticationTokenPayloadEncoder
                .builder()
                .secretKey(secreteKey)
                .initializationVector(initializationVector)
                .encodeAlgorithm(encodeAlgorithm)
                .build();
        //when
        String encode = payloadEncoder.encode(payload);
        String decode = payloadEncoder.decode(encode);
        //then
        assertThat(encode).isNotEqualTo(payload);
        assertThat(decode).isEqualTo(payload);
    }


}