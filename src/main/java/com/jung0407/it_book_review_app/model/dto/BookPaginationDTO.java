package com.jung0407.it_book_review_app.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookPaginationDTO {

    private int page;
    private int size;
    private int totalListCnt;
    private boolean hasNextPage;

//    // 페이지 당 보여지는 도서 정보 최대 갯수
//    private int pageSize;
//
//    // 현재 페이지
//    int page;
//
//    // 총 도서 데이터 수
//    int totalItemCnt;
//
//    // 총 페이지 수
//    int totalPageCnt;

//    public BookPaginationDTO(Integer totalItemCnt, Integer page, Integer pageSize) {
//        this.pageSize = pageSize;
//
//        // 현재 페이지
//        this.page = page;
//
//        // 총 게시글 수
//        this.totalItemCnt = totalItemCnt;
//
//        // 총 페이지 수
////        this.totalPageCnt = (int) Math.ceil(totalItemCnt * 1.0 / this.pageSize);
//        this.totalPageCnt = (int) Math.ceil(totalItemCnt * 1.0 / this.pageSize);
//    }
}
