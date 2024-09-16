package com.luccas.poc_rotation_key.controller;

import com.luccas.poc_rotation_key.model.UserRequest;
import com.luccas.poc_rotation_key.model.UserResponse;
import com.luccas.poc_rotation_key.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable final String id) {
        return userService.getUser(id);
    }

    @GetMapping("/all/users")
    public ResponseEntity<List<UserResponse>> retrieveUser() {
        return ResponseEntity.ok(userService.retrieveAndUpdateUsers());
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody final UserRequest request) {
        userService.saveUser(request);
        return ResponseEntity.ok().build();
    }

}
