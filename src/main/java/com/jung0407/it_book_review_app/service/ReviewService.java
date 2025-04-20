package com.jung0407.it_book_review_app.service;

import com.jung0407.it_book_review_app.model.dto.ReviewPaginationDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.ReviewSearchConditionDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.BookResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ReviewPagingResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ReviewResponseDTO;
import com.jung0407.it_book_review_app.model.entity.ReviewEntity;
import com.jung0407.it_book_review_app.repository.ReviewRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepositoryCustom reviewRepositoryCustom;

    public ReviewPagingResponseDTO<List<ReviewResponseDTO>> getReviewList(Pageable pageable, ReviewSearchConditionDTO reviewSearchConditionDTO) {

        Page<ReviewEntity> reviewEntities = reviewRepositoryCustom.findAllBySearchCondition(pageable, reviewSearchConditionDTO);

        List<ReviewResponseDTO> reviewResponseDTOList = reviewEntities.stream()
                .map(ReviewResponseDTO::of)
                .collect(Collectors.toList());

        ReviewPaginationDTO reviewPaginationDTO = new ReviewPaginationDTO(
                (int) reviewEntities.getTotalElements()
                , pageable.getPageNumber()
                , pageable.getPageSize()
                , 9
        );

        return ReviewPagingResponseDTO.OK(reviewResponseDTOList, reviewPaginationDTO);
    }

//    public Slice<ReviewResponseDTO> getReviewList(Pageable pageable, ReviewSearchConditionDTO reviewSearchConditionDTO) {
//
////        Slice<ReviewEntity> reviewEntities = reviewRepositoryCustom.findAllBySearchCondition(pageable, reviewSearchConditionDTO);
////
////        List<ReviewResponseDTO> dtoList = reviewEntities
////                .getContent()
////                .stream()
////                .map(ReviewResponseDTO::of)
////                .collect(Collectors.toList());
////
////        return new SliceImpl<>(dtoList, pageable, reviewEntities.hasNext());
//
//        return reviewRepositoryCustom.findAllBySearchCondition(pageable, reviewSearchConditionDTO);
//    }
}
