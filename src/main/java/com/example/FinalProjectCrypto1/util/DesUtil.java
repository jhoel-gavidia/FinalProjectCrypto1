package com.example.FinalProjectCrypto1.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class DesUtil {

    // DES requiere una clave de 8 caracteres
    private static final String SECRET_KEY = "VENTAS01";

    private SecretKey getSecretKey() throws Exception {
        DESKeySpec keySpec = new DESKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(keySpec);
    }

    public String encrypt(String texto) {

        try {

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());

            byte[] textoCifrado = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(textoCifrado);

        } catch (Exception e) {
            throw new RuntimeException("Error al cifrar datos", e);
        }

    }

    public String decrypt(String textoCifrado) {

        try {

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());

            byte[] textoDescifrado = cipher.doFinal(
                    Base64.getDecoder().decode(textoCifrado)
            );

            return new String(textoDescifrado, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Error al descifrar datos", e);
        }

    }

}
