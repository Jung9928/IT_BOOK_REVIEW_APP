package com.jung0407.it_book_review_app.model.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookSearchConditionDTO {

//    private String searchDetailCategory;    // 검색 조건인 상세 카테고리 (제목, 저자, 출판사)
    private String searchValue;             // 검색 값
    private String searchMainCategory;      // 검색 조건인 메인 카테고리
    private String searchSubCategory;       // 검색 조건인 서브 카테고리
}
