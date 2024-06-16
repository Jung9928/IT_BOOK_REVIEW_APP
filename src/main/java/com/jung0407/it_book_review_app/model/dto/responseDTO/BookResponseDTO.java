package com.jung0407.it_book_review_app.model.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseDTO {

    private int book_id;

    private String isbn;

    private String title;

    private String author;

    private String publisher;

    private Timestamp publishDate;

    private String rating;

    private String regular_price;

    private String selling_price;

    private String main_category;

    private String sub_category;

    private String imgPath;

    private Timestamp modifiedAt;

    private String site;

    private String detailInfoPath;
}
