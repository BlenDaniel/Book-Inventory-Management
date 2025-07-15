package com.book.inventory.management.bookim.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.book.inventory.management.bookim.utils.ApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Base controller to standardize API responses.
 */
@Slf4j
public abstract class AbstractController {

    protected <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return buildResponse(ApiResponse.success(data), HttpStatus.OK);
    }

    protected <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        return buildResponse(ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .build(), HttpStatus.OK);
    }

    protected <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return buildResponse(ApiResponse.<T>builder()
                .success(true)
                .message("Resource created successfully")
                .data(data)
                .build(), HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return buildResponse(ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build(), HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<ApiResponse<T>> noContent() {
        return buildResponse(ApiResponse.<T>builder()
                .success(true)
                .message("No content")
                .build(), HttpStatus.NO_CONTENT);
    }

    protected <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        return buildResponse(ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build(), HttpStatus.BAD_REQUEST);
    }

    protected <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        return buildResponse(ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build(), status);
    }

    protected <T> ResponseEntity<ApiResponse<T>> error(String message, Collection<String> errors, HttpStatus status) {
        return buildResponse(ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .errors(errors)
                .build(), status);
    }

    protected <T> ResponseEntity<ApiResponse<Map<String, String>>> validationError(Map<String, String> errors) {
        return buildResponse(ApiResponse.<Map<String, String>>builder()
                .success(false)
                .message("Validation error")
                .data(errors)
                .build(), HttpStatus.BAD_REQUEST);
    }

    protected <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return buildResponse(ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build(), HttpStatus.NOT_FOUND);
    }

    protected <T> ResponseEntity<ApiResponse<T>> unauthorized(String message) {
        return buildResponse(ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build(), HttpStatus.UNAUTHORIZED);
    }

    protected <T> ResponseEntity<ApiResponse<T>> forbidden(String message) {
        return buildResponse(ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build(), HttpStatus.FORBIDDEN);
    }

    protected <T> ResponseEntity<ApiResponse<T>> internalError(String message) {
        return buildResponse(ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected void logAndRethrow(Exception e, String msg) {
        log.error(msg, e);
        throw new RuntimeException(msg, e);
    }

    private <T> ResponseEntity<ApiResponse<T>> buildResponse(ApiResponse<T> body, HttpStatus status) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}
