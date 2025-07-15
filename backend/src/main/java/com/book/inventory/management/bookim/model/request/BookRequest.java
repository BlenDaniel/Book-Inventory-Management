package com.book.inventory.management.bookim.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String title;
    private String author;
    private String isbn;
    private int quantity;
    private String genre;
    private String publisher;
    private String description;
    private String coverImage;
}
