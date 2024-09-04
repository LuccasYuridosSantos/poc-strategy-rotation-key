package com.luccas.poc_rotation_key.controller;

import com.luccas.poc_rotation_key.mapper.UserMapper;
import com.luccas.poc_rotation_key.model.UserPIIInsertRequest;
import com.luccas.poc_rotation_key.model.UserPIIResponse;
import com.luccas.poc_rotation_key.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          final UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserPIIResponse>> getAllUsers()
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(final UserPIIInsertRequest request)
            throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException {
        userService.saveUser(userMapper.mapToUserPII(request));
        return ResponseEntity.ok().build();
    }

}
