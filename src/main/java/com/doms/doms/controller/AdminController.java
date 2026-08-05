package com.doms.doms.controller;


import com.doms.doms.dto.CreateUserRequest;
import com.doms.doms.dto.UpdateUserRequest;
import com.doms.doms.dto.UserResponse;
import com.doms.doms.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {


    private final UserService userService;



    // Create User
    @PostMapping("/create-user")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUserRequest request) {

        return ResponseEntity.ok(
                userService.createUser(request)
        );
    }



    // Get All Users
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }



    // Get User By ID
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getUserById(id)
        );
    }



    // Update User
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(
                userService.updateUser(id, request)
        );
    }



    // Delete User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {


        userService.deleteUser(id);


        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }

}