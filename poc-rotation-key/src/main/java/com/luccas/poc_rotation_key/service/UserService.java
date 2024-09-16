package com.luccas.poc_rotation_key.service;

import com.luccas.poc_rotation_key.mapper.UserMapper;
import com.luccas.poc_rotation_key.model.EncryptedData;
import com.luccas.poc_rotation_key.model.UserDTO;
import com.luccas.poc_rotation_key.model.UserRequest;
import com.luccas.poc_rotation_key.model.UserResponse;
import com.luccas.poc_rotation_key.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final UserMapper userMapper;

    public UserService(final UserRepository userRepository,
                       final EncryptionService encryptionService,
                       final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.userMapper = userMapper;
    }

    public void saveUser(final UserRequest user) {
        this.userRepository.insert(userMapper.mapToUser(user));
    }

    public List<UserResponse> retrieveAndUpdateUsers() {

        final var usersToUpdate = new ArrayList<UserResponse>();

        final var users = userRepository.findAll();

        final var decryptUsers = userMapper.mapToUser(users);

        final var idsToUpdate = users.stream().filter(userDTO -> !ObjectUtils.nullSafeEquals(encryptionService.getCurrentKeyVersion(),
                userDTO.getEncrypted().getVersion())).map(UserDTO::getId).toList();

        decryptUsers.stream().filter(u -> idsToUpdate.contains(u.getId())).forEach(usersToUpdate::add);

        userRepository.saveAll(userMapper.mapToUserDTO(usersToUpdate));


        return decryptUsers;
    }

    public List<UserResponse> getUsers() {
        final var users = userRepository.findAll();
        final var newUsers = new ArrayList<UserResponse>();
        for (UserDTO userDTO : users) {
            final var response = userMapper.mapToUser(userDTO);
            newUsers.add(response);
        }

        return newUsers;
    }

    private UserResponse getDecryptedUser(final UserDTO userDTO,
                                          final String version)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        final var userResponse = new UserResponse();
        userResponse.setId(userDTO.getId());
        userResponse.setUserId(userDTO.getUserId());
        userResponse.setCountry(userDTO.getCountry());
        if (ObjectUtils.nullSafeEquals(encryptionService.getCurrentKeyVersion(), version)) {
            userResponse.setFirstname(encryptionService.decrypt(userDTO.getFirstname()));
            userResponse.setLastname(encryptionService.decrypt(userDTO.getLastname()));
            userResponse.setEmail(encryptionService.decrypt(userDTO.getEmail()));
            userResponse.setCellphone(encryptionService.decrypt(userDTO.getCellphone()));
            userResponse.setCity(encryptionService.decrypt(userDTO.getCity()));
            userResponse.setZipcode(encryptionService.decrypt(userDTO.getZipcode()));

        } else if (encryptionService.isValidVersion(version)) {
//            Strategy find data contains different version encryption
            userResponse.setFirstname(encryptionService.specificDecrypt(userDTO.getFirstname(), version));
            userResponse.setLastname(encryptionService.specificDecrypt(userDTO.getLastname(), version));
            userResponse.setEmail(encryptionService.specificDecrypt(userDTO.getEmail(), version));
            userResponse.setCellphone(encryptionService.specificDecrypt(userDTO.getCellphone(), version));
            userResponse.setCity(encryptionService.specificDecrypt(userDTO.getCity(), version));
            userResponse.setZipcode(encryptionService.specificDecrypt(userDTO.getZipcode(), version));

        } else {
            throw new RuntimeException("Invalid version");
        }
        return userResponse;
    }

    private void insertEncryptedUser(final UserRequest user)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {
        final var userInsert = new UserDTO();
        userInsert.setUserId(UUID.randomUUID().toString());
        userInsert.setFirstname(encryptionService.encrypt(user.firstname()));
        userInsert.setLastname(encryptionService.encrypt(user.lastname()));
        userInsert.setEmail(encryptionService.encrypt(user.email()));
        userInsert.setCellphone(encryptionService.encrypt(user.cellphone()));
        userInsert.setCity(encryptionService.encrypt(user.city()));
        userInsert.setCountry(user.country());
        userInsert.setZipcode(encryptionService.encrypt(user.zipcode()));
        userInsert.setEncrypted(new EncryptedData(encryptionService.getCurrentKeyVersion(),
                Instant.now().getEpochSecond()));
        userRepository.insert(userInsert);
    }

    public ResponseEntity<UserResponse> getUser(final String id) {
        final var userDTO = userRepository.findById(id);
        return userDTO.map(u -> ResponseEntity.ok(userMapper.mapToUser(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
