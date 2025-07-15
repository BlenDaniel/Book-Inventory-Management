package com.book.inventory.management.bookim.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;

import com.book.inventory.management.bookim.model.User;
import com.book.inventory.management.bookim.model.dto.UserDto;
import com.book.inventory.management.bookim.model.request.UserLoginRequest;
import com.book.inventory.management.bookim.model.request.UserRegisterRequest;
import com.book.inventory.management.bookim.service.AuthService;
import com.book.inventory.management.bookim.utils.ApiResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController extends AbstractController {

    private final AuthService authService;

    // Create
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody UserRegisterRequest request) {
        try {
            UserDto userDto = authService.register(request);
            return ok(userDto);
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage());
            return error("Failed to register user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> login(@RequestBody UserLoginRequest request) {
        try {
            UserDto userDto = authService.login(request);
            return ok(userDto);
        } catch (Exception e) {
            log.error("Error logging in user: {}", e.getMessage());
            return error("Failed to log in user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Logout
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        try {
            authService.logout();
            return ok("User logged out successfully");
        } catch (Exception e) {
            log.error("Error logging out user: {}", e.getMessage());
            return error("Failed to log out user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
