package com.book.inventory.management.bookim.utils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;

/**
 * Generic API Response wrapper for consistent HTTP responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private String errorCode;
    private int status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private T data;
    private Collection<String> errors;

    @JsonIgnore
    private HttpStatus httpStatus;

    private Map<String, Object> meta;

    // --- Static factory methods ---

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message("Request successful")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message(message)
                .build();
    }

    public static <T> ApiResponse<Object> created(T data) {
        return baseBuilder(HttpStatus.CREATED, true)
                .message("Resource created")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<Object> error(String message, HttpStatus status) {
        return baseBuilder(status, false)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<Object> error(String message, Collection<String> errors, HttpStatus status) {
        return baseBuilder(status, false)
                .message(message)
                .errors(errors)
                .build();
    }

    // --- Internal builder initializer ---
    private static <T> ApiResponseBuilder<T> baseBuilder(HttpStatus status, boolean success) {
        return ApiResponse.<T>builder()
                .success(success)
                .status(status.value())
                .httpStatus(status)
                .timestamp(LocalDateTime.now());
    }
}
