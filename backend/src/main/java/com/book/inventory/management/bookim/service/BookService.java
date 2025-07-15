package com.book.inventory.management.bookim.service;

import java.util.List;

import com.book.inventory.management.bookim.model.dto.BookDto;
import com.book.inventory.management.bookim.model.request.BookCreateRequest;
import com.book.inventory.management.bookim.model.request.BookUpdateRequest;

public interface BookService {

    BookDto create(BookCreateRequest request);
    
    BookDto getOne(String id);
    
    List<BookDto> getAll();
    
    BookDto update(BookUpdateRequest request);
    
    void delete(String id);
    
    List<BookDto> search(String query);
}
