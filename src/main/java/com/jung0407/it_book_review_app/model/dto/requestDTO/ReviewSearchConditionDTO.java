package com.jung0407.it_book_review_app.model.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewSearchConditionDTO {

    private int bookId;
    private String isbn;                  // 도서 isbn pk값
    private String reviewSite;            // 리뷰 출처
}
