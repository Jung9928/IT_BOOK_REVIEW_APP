package com.jung0407.it_book_review_app.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "reviews", indexes = @Index(name = "idx_isbn", columnList = "isbn"))
@NoArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int review_id;

    @Column(name = "book_id")
    private int bookId;

    @Column(name = "isbn")
    private String isbn;

    @Column(length = 1000)
    private String review_title;

    @Column(length = 10)
    private String review_rating;

    @Column(length = 50)
    private String reviewer;

    @Column(name = "review_date")
    private Timestamp reviewDate;

    @Column(name = "review_content", columnDefinition = "TEXT")
    private String reviewContent;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @Column(name = "review_site", length = 30)
    private String reviewSite;

    @PreUpdate
    void modDtm() {
        this.modifiedAt = Timestamp.from(Instant.now());
    }
}
