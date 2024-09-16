package com.luccas.schedulerpii.service;

import com.luccas.schedulerpii.properties.KeyVersionProperties;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class EncryptionService {

    public static final String AES = "AES";


    private final KeyVersionProperties properties;

    public EncryptionService(final KeyVersionProperties properties) {
        this.properties = properties;
    }

    public String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(isEmpty(data)){
            return data;
        }
        Key key  =  generateKey(this.getCurrentKeyVersion());
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] encryptedValue = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public String specificDecrypt(final String encryptedData, final String version)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        if(isEmpty(encryptedData)){
            return encryptedData;
        }
        Key key  =  generateKey(version);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] decryptedValue = cipher.doFinal(Base64.getMimeDecoder().decode(encryptedData));
        return new String(decryptedValue);
    }

    public String getCurrentKeyVersion() {
        return this.properties.getActiveKeyVersion();
    }

    private Key generateKey(final String version) {
        return new SecretKeySpec(this.properties.getKeys().get(version.toUpperCase()).getBytes(),AES);
    }
}
