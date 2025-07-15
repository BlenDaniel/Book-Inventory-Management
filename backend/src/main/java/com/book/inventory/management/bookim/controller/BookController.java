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
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;

import com.book.inventory.management.bookim.model.dto.BookDto;
import com.book.inventory.management.bookim.model.request.BookCreateRequest;
import com.book.inventory.management.bookim.model.request.BookUpdateRequest;
import com.book.inventory.management.bookim.service.BookService;
import com.book.inventory.management.bookim.utils.ApiResponse;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController extends AbstractController {

    private final BookService bookService;

    // Create
    @PostMapping
    public ResponseEntity<ApiResponse<BookDto>> create(@RequestBody BookCreateRequest request) {
        try {
            BookDto bookDto = bookService.create(request);
            return ok(bookDto);
        } catch (Exception e) {
            log.error("Error creating book: {}", e.getMessage());
            return error("Failed to create book", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> getOne(@PathVariable String id) {
        try {
            BookDto bookDto = bookService.getOne(id);
            return ok(bookDto);
        } catch (Exception e) {
            log.error("Error getting book: {}", e.getMessage());
            return error("Failed to get book", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Read All
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDto>>> getAll() {
        try {
            List<BookDto> bookDtoList = bookService.getAll();
            return ok(bookDtoList);
        } catch (Exception e) {
            log.error("Error getting books: {}", e.getMessage());
            return error("Failed to get books", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update
    @PutMapping
    public ResponseEntity<ApiResponse<BookDto>> update(@RequestBody BookUpdateRequest request) {
        try {
            BookDto bookDto = bookService.update(request);
            return ok(bookDto);
        } catch (Exception e) {
            log.error("Error updating book: {}", e.getMessage());
            return error("Failed to update book", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> delete(@RequestBody BookUpdateRequest request) {
        try {
            bookService.delete(request.getId());
            return ok("Book deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting book: {}", e.getMessage());
            return error("Failed to delete book", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookDto>>> search(@RequestParam String query) {
        try {
            List<BookDto> bookDtoList = bookService.search(query);
            return ok(bookDtoList);
        } catch (Exception e) {
            log.error("Error searching books: {}", e.getMessage());
            return error("Failed to search books", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
