package com.info.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PasswordUtil {
    public static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);

    private static String encryptionKey = "MZygpewJsCpRrfOr";

    public static void main(String[] args) {
        logger.info("generateBase64Hash: {}", generateBase64Hash("AE90005555", "Qwe_123!"));
    }

    public static String generateBase64Hash(String userId, String password) {
        String originalInput = userId + ":" + password;
        return Base64.getEncoder().encodeToString(originalInput.getBytes());
    }

    private static Cipher getCipher(int cipherMode) throws Exception {
        String encryptionAlgorithm = "AES";
        SecretKeySpec keySpecification = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), encryptionAlgorithm);
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(cipherMode, keySpecification);
        return cipher;
    }

}
