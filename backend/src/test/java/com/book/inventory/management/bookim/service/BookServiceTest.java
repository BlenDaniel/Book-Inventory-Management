package com.book.inventory.management.bookim.service;

import com.book.inventory.management.bookim.mapper.BooksMapper;
import com.book.inventory.management.bookim.model.Book;
import com.book.inventory.management.bookim.model.dto.BookDto;
import com.book.inventory.management.bookim.model.request.BookCreateRequest;
import com.book.inventory.management.bookim.model.request.BookUpdateRequest;
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
        savedBook.setId("123");
        savedBook.setTitle("Test Book");
        savedBook.setAuthor("Test Author");
        savedBook.setIsbn("978-0-123456-78-9");
        savedBook.setPublishedYear(2023);
        savedBook.setGenre("Fiction");
        savedBook.setPrice(19.99);

        BookDto expectedDto = new BookDto();
        expectedDto.setId("123");
        expectedDto.setTitle("Test Book");
        expectedDto.setAuthor("Test Author");
        expectedDto.setIsbn("978-0-123456-78-9");
        expectedDto.setPublishedYear(2023);
        expectedDto.setGenre("Fiction");
        expectedDto.setPrice(19.99);

        // When
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(booksMapper.toDto(any(Book.class))).thenReturn(expectedDto);
        BookDto result = bookService.create(request);

        // Then
        verify(bookRepository).save(any(Book.class));
        verify(booksMapper).toDto(any(Book.class));
        assertEquals(expectedDto, result);
    }

    @Test
    void shouldGetBookById() {
        // Given
        Book book = new Book();
        book.setId("123");
        book.setTitle("Test Book");

        BookDto expectedDto = new BookDto();
        expectedDto.setId("123");
        expectedDto.setTitle("Test Book");

        // When
        when(bookRepository.findById("123")).thenReturn(Optional.of(book));
        when(booksMapper.toDto(any(Book.class))).thenReturn(expectedDto);
        BookDto result = bookService.getOne("123");

        // Then
        verify(bookRepository).findById("123");
        verify(booksMapper).toDto(any(Book.class));
        assertEquals(expectedDto, result);
    }

    @Test
    void shouldReturnAllBooks() {
        // Given
        Book book1 = new Book();
        book1.setId("123");
        book1.setTitle("Book 1");

        Book book2 = new Book();
        book2.setId("456");
        book2.setTitle("Book 2");

        BookDto dto1 = new BookDto();
        dto1.setId("123");
        dto1.setTitle("Book 1");

        BookDto dto2 = new BookDto();
        dto2.setId("456");
        dto2.setTitle("Book 2");

        // When
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));
        when(booksMapper.toDto(any(Book.class))).thenReturn(dto1, dto2);
        List<BookDto> result = bookService.getAll();

        // Then
        verify(bookRepository).findAll();
        verify(booksMapper, times(2)).toDto(any(Book.class));
        assertEquals(Arrays.asList(dto1, dto2), result);
    }

    @Test
    void shouldUpdateBook() {
        // Given
        BookUpdateRequest request = new BookUpdateRequest();
        request.setId("123");
        request.setTitle("Updated Title");
        request.setPrice(29.99);

        Book existingBook = new Book();
        existingBook.setId("123");
        existingBook.setTitle("Old Title");
        existingBook.setPrice(19.99);

        Book updatedBook = new Book();
        updatedBook.setId("123");
        updatedBook.setTitle("Updated Title");
        updatedBook.setPrice(29.99);

        BookDto expectedDto = new BookDto();
        expectedDto.setId("123");
        expectedDto.setTitle("Updated Title");
        expectedDto.setPrice(29.99);

        // When
        when(bookRepository.findById("123")).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(booksMapper.toDto(any(Book.class))).thenReturn(expectedDto);
        BookDto result = bookService.update(request);

        // Then
        verify(bookRepository).findById("123");
        verify(bookRepository).save(any(Book.class));
        verify(booksMapper).toDto(any(Book.class));
        assertEquals(expectedDto, result);
    }

    @Test
    void shouldDeleteBook() {
        // Given
        String bookId = "123";
        when(bookRepository.existsById(bookId)).thenReturn(true);

        // When
        bookService.delete(bookId);

        // Then
        verify(bookRepository).existsById(bookId);
        verify(bookRepository).deleteById(bookId);
    }

    @Test
    void shouldSearchBooks() {
        // Given
        Book book1 = new Book();
        book1.setId("123");
        book1.setTitle("Test Book");
        book1.setAuthor("Test Author");

        BookDto dto1 = new BookDto();
        dto1.setId("123");
        dto1.setTitle("Test Book");
        dto1.setAuthor("Test Author");

        // When
        when(bookRepository.searchByTitleOrAuthor("test")).thenReturn(Arrays.asList(book1));
        when(booksMapper.toDto(any(Book.class))).thenReturn(dto1);
        List<BookDto> result = bookService.search("test");

        // Then
        verify(bookRepository).searchByTitleOrAuthor("test");
        verify(booksMapper).toDto(any(Book.class));
        assertEquals(Arrays.asList(dto1), result);
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