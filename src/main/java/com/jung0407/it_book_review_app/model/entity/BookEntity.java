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
@Table(name = "books", indexes = @Index(name = "idx_book_id", columnList = "book_id"))
@NoArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;

    @Column(name = "isbn", length = 30)
    private String isbn;

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String author;

    @Column(length = 100)
    private String publisher;

    @Column(name = "publish_date")
    private Timestamp publishDate;

    @Column(length = 20)
    private String rating;

    @Column(length = 100)
    private String regular_price;

    @Column(length = 100)
    private String selling_price;

    @Column(name = "main_ctg_num", length = 100)
    private String main_category;

    @Column(name = "sub_ctg_num", length = 100)
    private String sub_category;

    @Column(name = "img_path", length = 1000)
    private String imgPath;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @Column(length = 30)
    private String site;

    @Column(name = "detail_info_path", length = 200)
    private String detailInfoPath;

    @PreUpdate
    void modDtm() {
        this.modifiedAt = Timestamp.from(Instant.now());
    }

//    public static BookEntity createBookEntity() {
//        BookEntity bookEntity = new BookEntity();
//
//        bookEntity.setBook_id();
//
//        return bookEntity;
//    }
}
