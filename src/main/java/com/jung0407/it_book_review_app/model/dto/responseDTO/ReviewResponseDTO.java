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
public class ReviewResponseDTO {

    private int review_id;

    private int book_id;

    private String isbn;

    private String review_title;

    private String review_rating;

    private String reviewer;

    private Timestamp review_date;

    private String review_content;

    private Timestamp modifiedAt;

    private String review_site;
}
