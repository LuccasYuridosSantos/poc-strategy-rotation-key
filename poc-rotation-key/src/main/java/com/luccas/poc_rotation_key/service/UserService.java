package com.luccas.poc_rotation_key.service;

import com.luccas.poc_rotation_key.model.EncryptedData;
import com.luccas.poc_rotation_key.model.UserPII;
import com.luccas.poc_rotation_key.model.UserPIIResponse;
import com.luccas.poc_rotation_key.repository.UserRepository;
import com.luccas.poc_rotation_key.security.EncryptionService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    public UserService(final UserRepository userRepository,
                       final EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
    }

    public void saveUser(final UserPII user)
            throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException {
        // Encrypt user
        user.setFirstname(encryptionService.encrypt(user.getFirstname()));
        user.setLastname(encryptionService.encrypt(user.getLastname()));
        user.setEmail(encryptionService.encrypt(user.getEmail()));
        user.setCellphone(encryptionService.encrypt(user.getCellphone()));
        user.setCity(encryptionService.encrypt(user.getCity()));
        user.setZipcode(encryptionService.encrypt(user.getZipcode()));
        user.setEncrypted(new EncryptedData(encryptionService.getCurrentKeyVersion(), Instant.now().getEpochSecond()));
        userRepository.insert(user);
    }

    public List<UserPIIResponse> getUsers()
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        final var users = userRepository.findAll();
        final var newUsers = new ArrayList<UserPIIResponse>();
        for (UserPII user : users) {
            final var response = decrypt(user, user.getEncrypted().getVersion());
            newUsers.add(response);
        }

        return newUsers;
    }

    private UserPIIResponse decrypt(final UserPII user,
                            final String version)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        final var userResponse = new UserPIIResponse();
        userResponse.setId(user.getId());
        userResponse.setUserId(user.getUserId());
        userResponse.setCountry(user.getCountry());
        if(ObjectUtils.nullSafeEquals(encryptionService.getCurrentKeyVersion(), version)) {
                  userResponse.setFirstname(encryptionService.decrypt(user.getFirstname()));
                    userResponse.setLastname(encryptionService.decrypt(user.getLastname()));
                    userResponse.setEmail(encryptionService.decrypt(user.getEmail()));
                    userResponse.setCellphone(encryptionService.decrypt(user.getCellphone()));
                    userResponse.setCity(encryptionService.decrypt(user.getCity()));
                    userResponse.setZipcode(encryptionService.decrypt(user.getZipcode()));

        } else if(encryptionService.isValidVersion(version)) {
            userResponse.setFirstname(encryptionService.specificDecrypt(user.getFirstname(), version));
            userResponse.setLastname(encryptionService.specificDecrypt(user.getLastname(), version));
            userResponse.setEmail(encryptionService.specificDecrypt(user.getEmail(), version));
            userResponse.setCellphone(encryptionService.specificDecrypt(user.getCellphone(), version));
            userResponse.setCity(encryptionService.specificDecrypt(user.getCity(), version));
            userResponse.setZipcode(encryptionService.specificDecrypt(user.getZipcode(), version));
        }else {
            throw new RuntimeException("Invalid version");
        }
        return userResponse;
    }
}
