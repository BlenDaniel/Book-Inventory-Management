package com.book.inventory.management.bookim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookimApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookimApplication.class, args);
	}

}
