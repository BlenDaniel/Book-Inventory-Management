package com.book.inventory.management.bookim.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.inventory.management.bookim.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByUserId(Long userId);

    Optional<Book> findByTitle(String title);

    Optional<Book> findByPublishedYear(int publishedYear);

    Optional<Book> findByAuthor(String author);

}
