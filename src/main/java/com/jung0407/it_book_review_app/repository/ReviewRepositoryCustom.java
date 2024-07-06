package com.jung0407.it_book_review_app.repository;

import com.jung0407.it_book_review_app.model.dto.requestDTO.ReviewSearchConditionDTO;
import com.jung0407.it_book_review_app.model.entity.ReviewEntity;
import com.jung0407.it_book_review_app.util.ReviewSource;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

//        return new PageImpl<>(results, pageable, total);
        return PageableExecutionUtils.getPage(results, pageable, queryCount::fetchOne);
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
}
