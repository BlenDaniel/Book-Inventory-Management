package com.book.inventory.management.bookim.controller;

import com.book.inventory.management.bookim.model.Book;
import com.book.inventory.management.bookim.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class BookControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        bookRepository.deleteAll();
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

        bookRepository.save(book);

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

        bookRepository.saveAll(Arrays.asList(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(2)));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        Book book = new Book();
        book.setId("123");
        book.setTitle("Old Title");
        book.setAuthor("Old Author");
        book.setIsbn("978-0-123456-78-9");
        book.setPublishedYear(2023);
        book.setGenre("Fiction");
        book.setPrice(19.99);

        bookRepository.save(book);

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

    @Test
    void shouldDeleteBook() throws Exception {
        Book book = new Book();
        book.setId("123");
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("978-0-123456-78-9");
        book.setPublishedYear(2023);
        book.setGenre("Fiction");
        book.setPrice(19.99);

        bookRepository.save(book);

        mockMvc.perform(delete("/api/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        mockMvc.perform(get("/api/books/{id}", book.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldSearchBooks() throws Exception {
        Book book1 = new Book();
        book1.setTitle("Java Programming");
        book1.setAuthor("John Doe");
        book1.setIsbn("978-0-123456-78-9");
        book1.setPublishedYear(2023);
        book1.setGenre("Programming");
        book1.setPrice(29.99);

        Book book2 = new Book();
        book2.setTitle("Python Guide");
        book2.setAuthor("Jane Smith");
        book2.setIsbn("978-0-987654-32-1");
        book2.setPublishedYear(2022);
        book2.setGenre("Programming");
        book2.setPrice(24.99);

        bookRepository.saveAll(Arrays.asList(book1, book2));

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
        mockMvc.perform(get("/api/books/{id}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }
} 