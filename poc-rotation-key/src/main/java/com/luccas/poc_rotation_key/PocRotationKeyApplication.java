package com.luccas.poc_rotation_key;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class PocRotationKeyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocRotationKeyApplication.class, args);
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = new SecureRandom();
			keyGenerator.init(128,secureRandom);
			SecretKey secretKey = keyGenerator.generateKey();
			String base64Key  = Base64.getEncoder().encodeToString(secretKey.getEncoded());
			System.out.println(base64Key);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
