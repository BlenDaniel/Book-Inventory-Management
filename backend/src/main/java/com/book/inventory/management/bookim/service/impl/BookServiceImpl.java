package com.book.inventory.management.bookim.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.book.inventory.management.bookim.exception.BookNotFoundException;
import com.book.inventory.management.bookim.mapper.BooksMapper;
import com.book.inventory.management.bookim.model.Book;
import com.book.inventory.management.bookim.model.dto.BookDto;
import com.book.inventory.management.bookim.model.request.BookCreateRequest;
import com.book.inventory.management.bookim.model.request.BookUpdateRequest;
import com.book.inventory.management.bookim.repository.BookRepository;
import com.book.inventory.management.bookim.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    private final BooksMapper booksMapper;

    @Override
    public BookDto create(BookCreateRequest request) {
        log.info("Creating new book with title: {}", request.getTitle());
        
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishedYear(request.getPublishedYear());
        book.setGenre(request.getGenre());
        book.setPrice(request.getPrice());
        
        Book savedBook = bookRepository.save(book);
        log.info("Successfully created book with id: {}", savedBook.getId());
        
        return booksMapper.toDto(savedBook);
    }

    @Override
    public BookDto getOne(String id) {
        log.info("Fetching book with id: {}", id);
        
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException("Book not found"));
        
        return booksMapper.toDto(book);
    }

    @Override
    public List<BookDto> getAll() {
        log.info("Fetching all books");
        
        List<Book> books = bookRepository.findAll();
        
        return books.stream()
            .map(booksMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public BookDto update(BookUpdateRequest request) {
        log.info("Updating book with id: {}", request.getId());
        
        Book existingBook = bookRepository.findById(request.getId())
            .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + request.getId()));
        
        existingBook.setTitle(request.getTitle());
        existingBook.setAuthor(request.getAuthor());
        existingBook.setIsbn(request.getIsbn());
        existingBook.setPublishedYear(request.getPublishedYear());
        existingBook.setGenre(request.getGenre());
        existingBook.setPrice(request.getPrice());
        
        Book updatedBook = bookRepository.save(existingBook);
        log.info("Successfully updated book with id: {}", updatedBook.getId());
        
        return booksMapper.toDto(updatedBook);
    }

    @Override
    public void delete(String id) {
        log.info("Deleting book with id: {}", id);
        
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        
        bookRepository.deleteById(id);
        log.info("Successfully deleted book with id: {}", id);
    }

    @Override
    public List<BookDto> search(String query) {
        log.info("Searching books with query: {}", query);
        
        List<Book> books = bookRepository.searchByTitleOrAuthor(query);
        
        return books.stream()
            .map(booksMapper::toDto)
            .collect(Collectors.toList());
    }
}
