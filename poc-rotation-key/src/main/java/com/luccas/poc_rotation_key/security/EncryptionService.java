package com.luccas.poc_rotation_key.security;

import com.luccas.poc_rotation_key.service.KeyVersionManager;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptionService {

    public static final String AES = "AES";


    private final KeyVersionManager keyVersionManager;

    public EncryptionService(final KeyVersionManager keyVersionManager) {
        this.keyVersionManager = keyVersionManager;
    }

    public String encrypt(final String data) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        if(ObjectUtils.isEmpty(data)){
            return data;
        }
        Key key  =  generateKey();
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] encryptedValue = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedValue);

    }



    public String decrypt(final String encryptedData)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        if(ObjectUtils.isEmpty(encryptedData)){
            return encryptedData;
        }
        Key key  =  generateKey();
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] decryptedValue = cipher.doFinal(Base64.getMimeDecoder().decode(encryptedData));
        return new String(decryptedValue);
    }

    public String specificDecrypt(final String encryptedData, final String version)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        if(ObjectUtils.isEmpty(encryptedData)){
            return encryptedData;
        }
        Key key  =  new SecretKeySpec(this.keyVersionManager.getSpecificKey(version).getBytes(),AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] decryptedValue = cipher.doFinal(Base64.getMimeDecoder().decode(encryptedData));
        return new String(decryptedValue);
    }

    public String getCurrentKeyVersion() {
        return keyVersionManager.getCurrentKeyVersion();
    }

    public boolean isValidVersion(final String version) {
        return keyVersionManager.getSpecificKey(version) != null;
    }

    private Key generateKey(){
        return new SecretKeySpec(this.keyVersionManager.getCurrentKey().getBytes(),AES);
    }
}
