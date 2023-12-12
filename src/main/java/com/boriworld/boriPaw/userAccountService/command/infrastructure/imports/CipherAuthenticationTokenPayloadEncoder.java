package com.boriworld.boriPaw.userAccountService.command.infrastructure.imports;

import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

/**
 * Cipher 를 이용한 String payload 를 암호화 및 해독
 * <p>
 * builder()를 통해 객체를 초기화할 수 있습니다.
 * String encode(String payloadValue): 암호화된 String 반환
 * String decode(String payloadValue): 해독된 String 반환
 */
public final class CipherAuthenticationTokenPayloadEncoder implements AuthenticationTokenPayloadEncoder {
    private final EncodeAlgorithm algorithm; // 암호화 및 해독에 사용될 알고리즘
    private final SecretKey secretKey; // 암호화 및 해독에 사용될 비밀키
    private final IvParameterSpec ivParameterSpec; //initialization Vector

    private CipherAuthenticationTokenPayloadEncoder(Builder builder) {
        Objects.requireNonNull(builder.encodeAlgorithm); // EncodeAlgorithm 를 최우선 null 확인 필요합니다.
        Objects.requireNonNull(builder.secretKey);
        Objects.requireNonNull(builder.initializationVector);
        this.algorithm = setAlgorithm(builder.encodeAlgorithm);
        this.secretKey = setSecreteKey(builder.secretKey);
        this.ivParameterSpec = new IvParameterSpec(setInitializationVector(builder.initializationVector));
    }

    public static Builder builder() {
        return new Builder();
    }

    private EncodeAlgorithm setAlgorithm(EncodeAlgorithm encodeAlgorithm) {
        return encodeAlgorithm;
    }

    private SecretKey setSecreteKey(String key) {
        if (key.length() < algorithm.secretKeyLength()) {
            throw new AuthenticationTokenPayloadEncoderException("secrete key length must longer then 32");
        }
        return new SecretKeySpec(key.substring(0, algorithm.secretKeyLength()).getBytes(algorithm.charsets()), this.algorithm.algorithm());
    }

    private byte[] setInitializationVector(String iv) {

        if (iv.length() < algorithm.ivLength()) {
            throw new AuthenticationTokenPayloadEncoderException("initialization Vector length must longer then 32");
        }

        return iv.substring(0, algorithm.ivLength()).getBytes(algorithm.charsets());
    }

    @Override
    public String encode(String payloadValue) {
        Cipher cipher = getInstance();
        selectMode(cipher, Cipher.ENCRYPT_MODE);
        byte[] encrypted = getEncrypted(payloadValue, cipher);
        return Base64.getEncoder().encodeToString(encrypted);

    }

    @Override
    public String decode(String payloadValue) {
        Cipher cipher = getInstance();
        selectMode(cipher, Cipher.DECRYPT_MODE);
        byte[] decoded = getDecoded(payloadValue, cipher);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    private byte[] getDecoded(String payloadValue, Cipher cipher) {
        try {
            return cipher.doFinal(Base64.getDecoder().decode(payloadValue));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new AuthenticationTokenPayloadEncoderException(e.getMessage());
        }

    }

    private byte[] getEncrypted(String payloadValue, Cipher cipher) {
        try {
            return cipher.doFinal(payloadValue.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new AuthenticationTokenPayloadEncoderException(e.getMessage());
        }
    }

    private void selectMode(Cipher cipher, int decryptMode) {
        try {
            cipher.init(decryptMode, secretKey, ivParameterSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new AuthenticationTokenPayloadEncoderException(e.getMessage());
        }
    }

    private Cipher getInstance() {
        try {
            return Cipher.getInstance(algorithm.cipherType());//required cipher type
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new AuthenticationTokenPayloadEncoderException(e.getMessage());
        }
    }

    /**
     * AuthenticationTokenPayloadEncoder 생성용 빌더
     */
    public static class Builder {
        private String secretKey;
        private String initializationVector;
        private EncodeAlgorithm encodeAlgorithm;

        public Builder() {
        }

        public Builder secretKey(String secreteKey) {
            this.secretKey = secreteKey;
            return this;
        }

        public Builder initializationVector(String iv) {
            this.initializationVector = iv;
            return this;
        }

        public Builder encodeAlgorithm(EncodeAlgorithm algorithm) {
            this.encodeAlgorithm = algorithm;
            return this;
        }

        public AuthenticationTokenPayloadEncoder build() {
            return new CipherAuthenticationTokenPayloadEncoder(this);
        }
    }

}
