package com.luccas.schedulerpii.service;

import com.luccas.schedulerpii.mapper.UserMapper;
import com.luccas.schedulerpii.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class UserService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final EncryptionService encryptionService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void updateEncryptionByVersion() {
        final var users = this.userRepository.findAllByEncryptedVersionNot(this.encryptionService.getCurrentKeyVersion());
        final var decryptedUsers = this.userMapper.mapToUser(users);
        final var encryptedUsers = this.userMapper.mapToUserDTO(decryptedUsers);

        LOGGER.info("Updating encryption for {} users", encryptedUsers.size());

        this.userRepository.updateAll(encryptedUsers);

        LOGGER.info("Encryption updated for {} users", encryptedUsers.size());
    }

    public void updateEncryptionByTimestamp() {
//        strategy to update encryption by timestamp, you specify a timestamp and all users with a timestamp greater than that will be updated
        final var timestampValid = Instant.now().plus(1L, ChronoUnit.DAYS).toEpochMilli();
        final var users = this.userRepository.findAllByEncryptedTimestamp(timestampValid);
        final var decryptedUsers = this.userMapper.mapToUser(users);
        final var encryptedUsers = this.userMapper.mapToUserDTO(decryptedUsers);

        LOGGER.info("Updating encryption for {} users", encryptedUsers.size());

        this.userRepository.updateAll(encryptedUsers);

        LOGGER.info("Encryption updated for {} users", encryptedUsers.size());
    }
}
