package com.jung0407.it_book_review_app.controller;

import com.jung0407.it_book_review_app.model.dto.requestDTO.ReviewSearchConditionDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ReviewPagingResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ReviewResponseDTO;
import com.jung0407.it_book_review_app.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviewList")
    public ReviewPagingResponseDTO<List<ReviewResponseDTO>> getReviewList(
            @PageableDefault(size = 5, sort = {"reviewDate"}) Pageable pageable,
            ReviewSearchConditionDTO reviewSearchConditionDTO
    ) {
        log.info("book id : " + reviewSearchConditionDTO.getBookId());
        log.info("review book isbn : " + reviewSearchConditionDTO.getIsbn());
        log.info("review site : " + reviewSearchConditionDTO.getReviewSite());
        return reviewService.getReviewList(pageable, reviewSearchConditionDTO);
    }
}
