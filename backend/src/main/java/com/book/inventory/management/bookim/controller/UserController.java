package com.book.inventory.management.bookim.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;

import com.book.inventory.management.bookim.model.dto.BookDto;
import com.book.inventory.management.bookim.model.dto.UserDto;
import com.book.inventory.management.bookim.model.request.UserUpdateRequest;
import com.book.inventory.management.bookim.service.UserService;
import com.book.inventory.management.bookim.utils.ApiResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController extends AbstractController {

    private final UserService userService;

    // Get One
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getOne(@PathVariable String id) {
        try {
            UserDto userDto = userService.getOne(id);
            return ok(userDto);
        } catch (Exception e) {
            log.error("Error getting user: {}", e.getMessage());
            return error("Failed to get user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update
    @PutMapping
    public ResponseEntity<ApiResponse<UserDto>> update(@RequestBody UserUpdateRequest request) {
        try {
            UserDto userDto = userService.update(request);
            return ok(userDto);
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            return error("Failed to update user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> delete(@RequestBody UserUpdateRequest request) {
        try {
            userService.delete(request.getId());
            return ok("User deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage());
            return error("Failed to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get books by user
    @GetMapping("/{id}/books")
    public ResponseEntity<ApiResponse<List<BookDto>>> getBooksByUser(@PathVariable String id) {
        try {
            List<BookDto> bookDtoList = userService.getBooksByUser(id);
            return ok(bookDtoList);
        } catch (Exception e) {
            log.error("Error getting books by user: {}", e.getMessage());
            return error("Failed to get books by user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
