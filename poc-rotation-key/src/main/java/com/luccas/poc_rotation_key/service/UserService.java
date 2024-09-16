package com.luccas.poc_rotation_key.service;

import com.luccas.poc_rotation_key.mapper.UserMapper;
import com.luccas.poc_rotation_key.model.UserDTO;
import com.luccas.poc_rotation_key.model.UserRequest;
import com.luccas.poc_rotation_key.model.UserResponse;
import com.luccas.poc_rotation_key.repository.UserRepository;
import com.luccas.poc_rotation_key.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final UserMapper userMapper;

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
        final var userResponse = UserHelper.getDecryptedUser(userDTO, version, encryptionService);
        return userResponse;
    }

    private void insertEncryptedUser(final UserRequest user)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        final var userInsert = UserHelper.insertEncryptedUser(user, encryptionService);
        userRepository.insert(userInsert);
    }

    public ResponseEntity<UserResponse> getUser(final String id) {
        final var userDTO = userRepository.findById(id);
        return userDTO.map(u -> ResponseEntity.ok(userMapper.mapToUser(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
