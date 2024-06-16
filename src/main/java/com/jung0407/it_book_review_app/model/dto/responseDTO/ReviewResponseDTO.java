package com.jung0407.it_book_review_app.model.dto.responseDTO;

import com.jung0407.it_book_review_app.model.entity.ReviewEntity;
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

    public static ReviewResponseDTO of(ReviewEntity reviewEntity) {
        return ReviewResponseDTO.builder()
                .book_id(reviewEntity.getBookId())
                .isbn(reviewEntity.getIsbn())
                .review_id(reviewEntity.getReview_id())
                .review_title(reviewEntity.getReview_title())
                .review_rating(reviewEntity.getReview_rating())
                .reviewer(reviewEntity.getReviewer())
                .review_date(reviewEntity.getReviewDate())
                .review_content(reviewEntity.getReviewContent())
                .modifiedAt(reviewEntity.getModifiedAt())
                .review_site(reviewEntity.getReviewSite())
                .build();
    }
}
