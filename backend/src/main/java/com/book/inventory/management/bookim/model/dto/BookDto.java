package com.book.inventory.management.bookim.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private String genre;
    private double price;

}
