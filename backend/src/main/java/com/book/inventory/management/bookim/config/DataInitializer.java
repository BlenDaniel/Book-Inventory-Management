package com.book.inventory.management.bookim.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.book.inventory.management.bookim.model.Book;
import com.book.inventory.management.bookim.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if books already exist
        if (bookRepository.count() > 0) {
            log.info("Books already exist in database, skipping sample data initialization");
            return;
        }

        log.info("Initializing sample book data...");

        // Create sample books
        createSampleBooks();

        log.info("Sample book data initialization completed");
    }

    private void createSampleBooks() {
        Book[] sampleBooks = {
            new Book("To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4", 1960, "Fiction", 12.99),
            new Book("1984", "George Orwell", "978-0-452-28423-4", 1949, "Dystopian Fiction", 13.99),
            new Book("Pride and Prejudice", "Jane Austen", "978-0-14-143951-8", 1813, "Romance", 11.99),
            new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5", 1925, "Fiction", 14.99),
            new Book("The Catcher in the Rye", "J.D. Salinger", "978-0-316-76948-0", 1951, "Fiction", 13.49),
            new Book("Lord of the Flies", "William Golding", "978-0-571-05686-2", 1954, "Fiction", 12.49),
            new Book("The Hobbit", "J.R.R. Tolkien", "978-0-547-92822-7", 1937, "Fantasy", 15.99),
            new Book("Fahrenheit 451", "Ray Bradbury", "978-1-4516-7331-9", 1953, "Science Fiction", 13.99),
            new Book("Jane Eyre", "Charlotte Brontë", "978-0-14-144114-6", 1847, "Gothic Fiction", 12.99),
            new Book("The Lord of the Rings", "J.R.R. Tolkien", "978-0-544-00341-5", 1954, "Fantasy", 24.99),
            new Book("Brave New World", "Aldous Huxley", "978-0-06-085052-4", 1932, "Science Fiction", 14.49),
            new Book("The Chronicles of Narnia", "C.S. Lewis", "978-0-06-023481-4", 1950, "Fantasy", 19.99),
            new Book("Wuthering Heights", "Emily Brontë", "978-0-14-143955-6", 1847, "Gothic Fiction", 11.99),
            new Book("Animal Farm", "George Orwell", "978-0-452-28424-1", 1945, "Political Satire", 10.99),
            new Book("Of Mice and Men", "John Steinbeck", "978-0-14-017739-8", 1937, "Fiction", 12.49)
        };

        for (Book book : sampleBooks) {
            try {
                bookRepository.save(book);
                log.debug("Created book: {} by {}", book.getTitle(), book.getAuthor());
            } catch (Exception e) {
                log.error("Error creating book: {} - {}", book.getTitle(), e.getMessage());
            }
        }

        log.info("Created {} sample books", sampleBooks.length);
    }
} 