package com.jung0407.it_book_review_app.repository;

import com.jung0407.it_book_review_app.model.dto.requestDTO.ReviewSearchConditionDTO;
import com.jung0407.it_book_review_app.model.entity.QReviewEntity;
import com.jung0407.it_book_review_app.model.entity.ReviewEntity;
import com.jung0407.it_book_review_app.util.ReviewSource;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.jung0407.it_book_review_app.model.entity.QReviewEntity.reviewEntity;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<ReviewEntity> findAllBySearchCondition(Pageable pageable, ReviewSearchConditionDTO reviewSearchConditionDTO) {
        QReviewEntity review = reviewEntity;

        JPAQuery<ReviewEntity> query =
                queryFactory.selectFrom(reviewEntity).where(
                        searchBookId(reviewSearchConditionDTO.getBookId()),
//                        searchBookIsbn(reviewSearchConditionDTO.getIsbn()),
                        searchReviewSite(reviewSearchConditionDTO.getReviewSite())
                );

        JPAQuery<Long> queryCount =
                queryFactory.select(reviewEntity.count()).from(reviewEntity).where(
                        searchBookId(reviewSearchConditionDTO.getBookId()),
//                        searchBookIsbn(reviewSearchConditionDTO.getIsbn()),
                        searchReviewSite(reviewSearchConditionDTO.getReviewSite())
                );

        long total = query.stream().count();            // 전체 리뷰 데이터 카운트 후, 아래에서 조건 처리

        List<ReviewEntity> results = query
                .offset(((long)(pageable.getPageNumber())* pageable.getPageSize()))
                .limit(pageable.getPageSize())
                .orderBy(reviewEntity.reviewDate.desc())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable, queryCount::fetchOne);

//        List<ReviewResponseDTO> results = queryFactory
//                .select(Projections.constructor(ReviewResponseDTO.class,
//                        review.review_id,
//                        review.bookId,
//                        review.isbn,
//                        review.review_title,
//                        review.review_rating,
//                        review.reviewer,
//                        review.reviewDate,
//                        review.reviewContent,
//                        review.modifiedAt,
//                        review.reviewSite))
//                .from(review)
//                .where(
//                        searchBookId(reviewSearchConditionDTO.getBookId()),
//                        searchReviewSite(reviewSearchConditionDTO.getReviewSite())
//                )
//                .orderBy(review.reviewDate.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize()+1)                                    // slice 방식 : +1해서 hasNext 판별
//                .fetch();
//
//        boolean hasNext = results.size() > pageable.getPageSize();
//
//        if(hasNext) {
//            results.remove(pageable.getPageSize());                                 // 초과분 제거
//        }
//
//        return new SliceImpl<>(results, pageable, hasNext);
    }

    private BooleanExpression searchBookId(int id) {
        return reviewEntity.bookId.eq(id);
    }

    private BooleanExpression searchReviewSite(String review_site) {
        if(StringUtils.hasLength(review_site)) {
            if(ReviewSource.YES24.equals(review_site)) {
                return reviewEntity.reviewSite.contains(review_site);
            }
        }

        return null;
    }

//    private BooleanExpression searchBookId(int id) {
//        return reviewEntity.bookId.eq(id);
//    }
//
//    private BooleanExpression searchReviewSite(String review_site) {
//        if(StringUtils.hasLength(review_site)) {
//            if(ReviewSource.YES24.equals(review_site)) {
//                return reviewEntity.reviewSite.eq(review_site);
//            }
//        }
//
//        return null;
//    }
//
//    private BooleanExpression searchIsbn(String isbn) {
//        if(StringUtils.hasText(isbn)) {
//            return reviewEntity.isbn.eq(isbn);
//        }
//        return null;
//    }
}
