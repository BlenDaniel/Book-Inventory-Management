package com.book.inventory.management.bookim.mapper;

import org.springframework.stereotype.Service;

import com.book.inventory.management.bookim.model.Book;
import com.book.inventory.management.bookim.model.dto.BookDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BooksMapper implements BaseMapper<Book, BookDto> {

    @Override
    public BookDto toDto(Book entity) {
        if (entity == null)
            return null;
        return new BookDto(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getIsbn(),
                entity.getPublishedYear(),
                entity.getGenre(),
                entity.getPrice());
    }

    @Override
    public Book toEntity(BookDto dto) {
        if (dto == null)
            return null;
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPublishedYear(dto.getPublishedYear());
        book.setGenre(dto.getGenre());
        book.setPrice(dto.getPrice());
        return book;
    }
}
