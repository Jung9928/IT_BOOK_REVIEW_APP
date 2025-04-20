package com.jung0407.it_book_review_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ItBookReviewAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItBookReviewAppApplication.class, args);
    }

}
