package com.boriworld.boriPaw.userAccountService.command.infrastructure.imports;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public enum EncodeAlgorithm {
    AES("AES", "AES/CBC/PKCS5Padding", 32, 16, StandardCharsets.UTF_8);
    private final String ALGORITHM_NAME;
    private final String CIPHER_TYPE;
    private final int SECRET_KEY_LENGTH;
    private final int INITIALIZATION_VECTOR_LENGTH;
    private final Charset CHARSETS;

    EncodeAlgorithm(String ALGORITHM_NAME, String CIPHER_TYPE, int SECRET_KEY_LENGTH, int INITIALIZATION_VECTOR_LENGTH, Charset CHARSETS) {
        this.ALGORITHM_NAME = ALGORITHM_NAME;
        this.CIPHER_TYPE = CIPHER_TYPE;
        this.SECRET_KEY_LENGTH = SECRET_KEY_LENGTH;
        this.INITIALIZATION_VECTOR_LENGTH = INITIALIZATION_VECTOR_LENGTH;
        this.CHARSETS = CHARSETS;
    }

    public String algorithm() {
        return this.ALGORITHM_NAME;
    }

    public String cipherType() {
        return this.CIPHER_TYPE;
    }

    public int secretKeyLength() {
        return this.SECRET_KEY_LENGTH;
    }

    public int ivLength() {
        return this.INITIALIZATION_VECTOR_LENGTH;
    }

    public Charset charsets() {
        return this.CHARSETS;
    }
}
