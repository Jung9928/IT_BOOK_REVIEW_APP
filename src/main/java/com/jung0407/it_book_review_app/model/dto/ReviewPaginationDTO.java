package com.jung0407.it_book_review_app.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewPaginationDTO {

//    // 페이지 당 보여지는 리뷰 최대 갯수
//    private int pageSize;
//
//    // 현재 페이지
//    int page;
//
//    // 다음 페이지 존재 여부 (Slice 의 hasNext())
//    private boolean hasNextPage;


    // 페이지 당 보여지는 리뷰 최대 갯수
    private int pageSize;

    // 현재 페이지
    int page;

    // 현재 블록
    int block;

    // 총 도서 데이터 수
    int totalListCnt;

    // 총 페이지 수
    int totalPageCnt;

    // 총 구간 수
    int totalBlockCnt;

    // 시작 페이지
    int startPage;

    // 마지막 페이지
    int endPage;

    // 이전 구간 마지막 페이지
    int prevBlock;

    // 다음 구간 시작 페이지
    int nextBlock;

    // 인덱스
    int startIndex;

    public ReviewPaginationDTO(Integer totalListCnt, Integer page, Integer pageSize, Integer blockSize) {
        this.pageSize = pageSize;

        // 현재 페이지
        this.page = page;

        // 총 게시글 수
        this.totalListCnt = totalListCnt;

        // 총 페이지 수
        totalPageCnt = (int) Math.ceil(totalListCnt * 1.0 / this.pageSize);

        // 총 블럭 수
        totalBlockCnt = (int) Math.ceil(totalPageCnt * 1.0 / blockSize);

        // 현재 블럭
        block = (int) Math.ceil((this.page * 1.0) / blockSize);

        // 블럭 시작 페이지
        startPage = ((block - 1) * blockSize + 1);

        // 블럭 마지막 페이지
        endPage = startPage + blockSize - 1;

        // 블럭 마지막 페이지 validation
        if (endPage > totalPageCnt) endPage = totalPageCnt;

        // 이전 블럭 (클릭 시, 이전 블럭 마지막 페이지)
        prevBlock = (block * blockSize) - blockSize;

        // 이전 블럭 validation
        if (prevBlock < 1) prevBlock = 1;

        // 다음 블럭 (클릭 시, 다음 블럭 첫번째 페이지)
        nextBlock = (block * blockSize + 1);

        // 다음 블럭 validation
        if (nextBlock > totalPageCnt) nextBlock = totalPageCnt;

        startIndex = (this.page - 1) * this.pageSize;
    }
}
