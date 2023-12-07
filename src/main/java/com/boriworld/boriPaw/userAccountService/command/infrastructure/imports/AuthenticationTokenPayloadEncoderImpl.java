package com.boriworld.boriPaw.userAccountService.command.infrastructure.imports;

import com.boriworld.boriPaw.userAccountService.command.domain.service.AuthenticationTokenPayloadEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class AuthenticationTokenPayloadEncoderImpl implements AuthenticationTokenPayloadEncoder {

    private static final String KEY = "f74fakfas2fsagfom3Axqweqrw1o2312";
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TYPE = "AES/CBC/PKCS5Padding";
    private static final byte[] IV = "DFSdgasghadfhadhe32rwgag".substring(0, 16).getBytes(); // Initialization Vector
    private static final SecretKey SECRET_KEY = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    private static final Cipher CIPHER;
    private static final IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

    static {
        try {
            CIPHER = Cipher.getInstance(CIPHER_TYPE);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }
    public String encode(String value) {

        try {
            CIPHER.init(Cipher.ENCRYPT_MODE, SECRET_KEY, ivParameterSpec);
        } catch (InvalidKeyException ignored) {
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        byte[] encrypted;
        try {
            encrypted = CIPHER.doFinal(value.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return new String(Base64.getEncoder().encode(encrypted));
    }

    public String decode(String payloadValue) {

        try {
            CIPHER.init(Cipher.DECRYPT_MODE, SECRET_KEY, ivParameterSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        try {
            return new String(CIPHER.doFinal(Base64.getDecoder().decode(payloadValue)), StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

}
