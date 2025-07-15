package com.book.inventory.management.bookim.service;

import com.book.inventory.management.bookim.mapper.BooksMapper;
import com.book.inventory.management.bookim.model.Book;
import com.book.inventory.management.bookim.model.dto.BookDto;
import com.book.inventory.management.bookim.model.request.BookCreateRequest;
import com.book.inventory.management.bookim.repository.BookRepository;
import com.book.inventory.management.bookim.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BooksMapper booksMapper;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, booksMapper);
    }

    @Test
    void shouldCreateBook() {
        // Given
        BookCreateRequest request = new BookCreateRequest();
        request.setTitle("Test Book");
        request.setAuthor("Test Author");
        request.setIsbn("978-0-123456-78-9");
        request.setPublishedYear(2023);
        request.setGenre("Fiction");
        request.setPrice(19.99);

        Book savedBook = new Book();
        savedBook.setId("test-id");
        savedBook.setTitle("Test Book");
        savedBook.setAuthor("Test Author");

        BookDto expectedDto = new BookDto();
        expectedDto.setId("test-id");
        expectedDto.setTitle("Test Book");
        expectedDto.setAuthor("Test Author");

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(booksMapper.toDto(savedBook)).thenReturn(expectedDto);

        // When
        BookDto result = bookService.create(request);

        // Then
        assertNotNull(result);
        assertEquals("test-id", result.getId());
        assertEquals("Test Book", result.getTitle());
        assertEquals("Test Author", result.getAuthor());
        verify(bookRepository).save(any(Book.class));
        verify(booksMapper).toDto(savedBook);
    }

    @Test
    void shouldGetAllBooks() {
        // Given
        Book book1 = new Book();
        book1.setId("1");
        book1.setTitle("Book 1");

        Book book2 = new Book();
        book2.setId("2");
        book2.setTitle("Book 2");

        List<Book> books = Arrays.asList(book1, book2);

        BookDto dto1 = new BookDto();
        dto1.setId("1");
        dto1.setTitle("Book 1");

        BookDto dto2 = new BookDto();
        dto2.setId("2");
        dto2.setTitle("Book 2");

        when(bookRepository.findAll()).thenReturn(books);
        when(booksMapper.toDto(book1)).thenReturn(dto1);
        when(booksMapper.toDto(book2)).thenReturn(dto2);

        // When
        List<BookDto> result = bookService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Book 2", result.get(1).getTitle());
        verify(bookRepository).findAll();
    }

    @Test
    void shouldGetOneBook() {
        // Given
        String bookId = "test-id";
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");

        BookDto expectedDto = new BookDto();
        expectedDto.setId(bookId);
        expectedDto.setTitle("Test Book");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(booksMapper.toDto(book)).thenReturn(expectedDto);

        // When
        BookDto result = bookService.getOne(bookId);

        // Then
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository).findById(bookId);
        verify(booksMapper).toDto(book);
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        // Given
        String bookId = "non-existent-id";
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> bookService.getOne(bookId));
        verify(bookRepository).findById(bookId);
        verifyNoInteractions(booksMapper);
    }
} 