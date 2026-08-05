package com.doms.doms.controller;

import com.doms.doms.dto.UpdateUserRequest;
import com.doms.doms.dto.UserResponse;
import com.doms.doms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    // Logged-in User Profile
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile() {

        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    // Update Logged-in User Profile
    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(
                userService.updateCurrentUser(request)
        );
    }
}