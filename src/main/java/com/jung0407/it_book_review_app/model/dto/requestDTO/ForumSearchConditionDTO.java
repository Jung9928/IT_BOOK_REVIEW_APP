package com.jung0407.it_book_review_app.model.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForumSearchConditionDTO {

    private String searchCategory;        // 검색 조건인 제목/작성자/내용 카테고리
    private String searchValue;           // 검색 값
}
