package com.book.inventory.management.bookim.controller;

import com.book.inventory.management.bookim.model.Book;
import com.book.inventory.management.bookim.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // No need to delete all since we're mocking the repository
    }

    @Test
    void shouldCreateBook() throws Exception {
        String bookJson = """
            {
                "title": "Test Book",
                "author": "Test Author",
                "isbn": "978-0-123456-78-9",
                "publishedYear": 2023,
                "genre": "Fiction",
                "price": 19.99
            }
            """;

        Book savedBook = new Book();
        savedBook.setId("123");
        savedBook.setTitle("Test Book");
        savedBook.setAuthor("Test Author");
        savedBook.setIsbn("978-0-123456-78-9");
        savedBook.setPublishedYear(2023);
        savedBook.setGenre("Fiction");
        savedBook.setPrice(19.99);

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Test Book"))
                .andExpect(jsonPath("$.data.author").value("Test Author"));
    }

    @Test
    void shouldGetBookById() throws Exception {
        Book book = new Book();
        book.setId("123");
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("978-0-123456-78-9");
        book.setPublishedYear(2023);
        book.setGenre("Fiction");
        book.setPrice(19.99);

        when(bookRepository.findById("123")).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Test Book"))
                .andExpect(jsonPath("$.data.author").value("Test Author"));
    }

    @Test
    void shouldGetAllBooks() throws Exception {
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setIsbn("978-0-123456-78-9");
        book1.setPublishedYear(2023);
        book1.setGenre("Fiction");
        book1.setPrice(19.99);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setIsbn("978-0-987654-32-1");
        book2.setPublishedYear(2022);
        book2.setGenre("Non-Fiction");
        book2.setPrice(24.99);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        Book existingBook = new Book();
        existingBook.setId("123");
        existingBook.setTitle("Old Title");
        existingBook.setAuthor("Old Author");
        existingBook.setIsbn("978-0-123456-78-9");
        existingBook.setPublishedYear(2023);
        existingBook.setGenre("Fiction");
        existingBook.setPrice(19.99);

        Book updatedBook = new Book();
        updatedBook.setId("123");
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setIsbn("978-0-123456-78-9");
        updatedBook.setPublishedYear(2023);
        updatedBook.setGenre("Fiction");
        updatedBook.setPrice(29.99);

        when(bookRepository.findById("123")).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        String updateJson = """
            {
                "id": "123",
                "title": "Updated Title",
                "author": "Updated Author",
                "price": 29.99
            }
            """;

        mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Updated Title"))
                .andExpect(jsonPath("$.data.price").value(29.99));
    }

    // @Test
    // void shouldDeleteBook() throws Exception {
    //     Book book = new Book();
    //     book.setId("123");
    //     book.setTitle("Test Book");
    //     book.setAuthor("Test Author");
    //     book.setIsbn("978-0-123456-78-9");
    //     book.setPublishedYear(2023);
    //     book.setGenre("Fiction");
    //     book.setPrice(19.99);

    //     when(bookRepository.findById("123")).thenReturn(Optional.of(book));
    //     doNothing().when(bookRepository).deleteById("123");

    //     mockMvc.perform(delete("/api/books/{id}", book.getId()))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.success").value(true));

    //     verify(bookRepository).deleteById("123");
    // }

    @Test
    void shouldSearchBooks() throws Exception {
        Book book1 = new Book();
        book1.setTitle("Java Programming");
        book1.setAuthor("John Doe");
        book1.setIsbn("978-0-123456-78-9");
        book1.setPublishedYear(2023);
        book1.setGenre("Programming");
        book1.setPrice(29.99);

        when(bookRepository.searchByTitleOrAuthor("Java")).thenReturn(Arrays.asList(book1));

        mockMvc.perform(get("/api/books/search")
                .param("query", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].title").value("Java Programming"));
    }

    @Test
    void shouldReturn404WhenBookNotFound() throws Exception {
        when(bookRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/{id}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }
} 