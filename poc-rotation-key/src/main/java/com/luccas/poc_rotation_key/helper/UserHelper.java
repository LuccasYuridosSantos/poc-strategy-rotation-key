package com.luccas.poc_rotation_key.helper;

import com.luccas.poc_rotation_key.model.EncryptedData;
import com.luccas.poc_rotation_key.model.UserDTO;
import com.luccas.poc_rotation_key.model.UserRequest;
import com.luccas.poc_rotation_key.model.UserResponse;
import com.luccas.poc_rotation_key.service.EncryptionService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;

public class UserHelper {
    public static UserResponse  getDecryptedUser(final UserDTO userDTO,
                                         final String version, final EncryptionService encryptionService)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        final var userResponse = new UserResponse().builder()
                .id(userDTO.getId())
                .userId(userDTO.getUserId())
                .country(userDTO.getCountry())
                .firstname(encryptionService.specificDecrypt(userDTO.getFirstname(), version))
                .lastname(encryptionService.specificDecrypt(userDTO.getFirstname(), version))
                .email(encryptionService.specificDecrypt(userDTO.getFirstname(), version))
                .cellphone(encryptionService.specificDecrypt(userDTO.getFirstname(), version))
                .city(encryptionService.specificDecrypt(userDTO.getFirstname(), version))
                .zipcode(encryptionService.specificDecrypt(userDTO.getFirstname(), version))
                .build();
        return userResponse;
    }

    public static UserDTO insertEncryptedUser(final UserRequest user, final EncryptionService encryptionService)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        final var userInsert = new UserDTO().builder()
                .userId(UUID.randomUUID().toString())
                .firstname(encryptionService.encrypt(user.firstname()))
                .lastname(encryptionService.encrypt(user.lastname()))
                .email(encryptionService.encrypt(user.email()))
                .cellphone(encryptionService.encrypt(user.cellphone()))
                .city(encryptionService.encrypt(user.city()))
                .country(user.country())
                .zipcode(encryptionService.encrypt(user.zipcode()))
                .encrypted(new EncryptedData(encryptionService.getCurrentKeyVersion(), Instant.now().getEpochSecond()))
                .build();
        return userInsert;
    }
}