package com.jung0407.it_book_review_app.repository;

import com.jung0407.it_book_review_app.model.dto.BookSliceWithCount;
import com.jung0407.it_book_review_app.model.dto.requestDTO.BookSearchConditionDTO;
import com.jung0407.it_book_review_app.model.entity.BookEntity;
import com.jung0407.it_book_review_app.util.BookMainCategory;
import com.jung0407.it_book_review_app.util.BookSubCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import java.util.Arrays;
import java.util.List;

import static com.jung0407.it_book_review_app.model.entity.QBookEntity.bookEntity;

// QueryDSL을 사용하므로 인터페이스가 아닌 클래스를 생성하여 JPAQueryFactory을 통해 구현
@RequiredArgsConstructor
@Repository
@Slf4j
public class BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookSliceWithCount findAllBySearchCondition(Pageable pageable, BookSearchConditionDTO bookSearchConditionDTO) {

        List<BookEntity> results = queryFactory
                .selectFrom(bookEntity)
                .where(
                        searchMainKeywords(bookSearchConditionDTO.getSearchMainCategory()),
                        searchSubKeywords(bookSearchConditionDTO.getSearchSubCategory()),
                        searchKeyword(bookSearchConditionDTO.getSearchValue())                      // 통합 검색 조건
                )
                .orderBy(bookEntity.publishDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)              // Slice 방식 : hasNext 판별을 위해 +1
                .fetch();

        boolean hasNext = false;

        if(results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(results.size() - 1);             // 초과 데이터 제거
        }

        // count 쿼리 별도 실행
        Long total = queryFactory
                .select(bookEntity.count())
                .from(bookEntity)
                .where(
                        searchMainKeywords(bookSearchConditionDTO.getSearchMainCategory()),
                        searchSubKeywords(bookSearchConditionDTO.getSearchSubCategory()),
                        searchKeyword(bookSearchConditionDTO.getSearchValue())
                )
                .fetchOne();

        return new BookSliceWithCount(results, total, hasNext);
    }



//    public Page<BookEntity> findAllBySearchCondition(Pageable pageable, BookSearchConditionDTO bookSearchConditionDTO) {
//
//        JPAQuery<BookEntity> query =
//                queryFactory.selectFrom(bookEntity).where(
//                        searchMainKeywords(bookSearchConditionDTO.getSearchMainCategory()),
//                        searchSubKeywords(bookSearchConditionDTO.getSearchSubCategory()),
//                        searchDetailKeywords(bookSearchConditionDTO.getSearchDetailCategory(), bookSearchConditionDTO.getSearchValue())
//                );
//
//        JPAQuery<Long> queryCount =
//                queryFactory.select(bookEntity.count()).from(bookEntity).where(
//                        searchMainKeywords(bookSearchConditionDTO.getSearchMainCategory()),
//                        searchSubKeywords(bookSearchConditionDTO.getSearchSubCategory()),
//                        searchDetailKeywords(bookSearchConditionDTO.getSearchDetailCategory(), bookSearchConditionDTO.getSearchValue())
//                );
//
//        long total = query.stream().count();        // 전체 도서 데이터 카운트 후, 아래에서 조건 처리에 활용
//
//        log.info("total count : " + total);
//        log.info("pageable.getPageNumber : " + pageable.getPageNumber());
//        log.info("pageable.getPageSize : " + pageable.getPageSize());
//        log.info("pageable.getPageNumber * pageable.getPageSize() : " + (long)(pageable.getPageNumber()) * pageable.getPageSize());
//        log.info("pageable.getPageNumber()-1 * pageable.getPageSize() : " + (long)(pageable.getPageNumber()-1) * pageable.getPageSize());
//        log.info("pageable.getOffset() : " + pageable.getOffset());
//
//        List<BookEntity> results = query
//                // 페이지 번호
//                // 프론트에서 맨 처음 로딩 시, 1페이지 값을 전달하므로 getPageNumber에 -1하여 offset 첫 값을 0설정
//                // ex) limit(0, 9) -> limit(9, 18) -> limit(18, 27)
////                .offset((long)(pageable.getPageNumber()-1) * pageable.getPageSize())
//                .offset((long)(pageable.getPageNumber()) * pageable.getPageSize())
//                .limit(pageable.getPageSize())      // 페이지에 표시할 도서 갯수
//                .orderBy(bookEntity.publishDate.desc())
//                .fetch();
//
//        log.info("results size : " + results.stream().count());
//
//        return PageableExecutionUtils.getPage(results, pageable, queryCount::fetchOne);
//    }

//    private BooleanExpression searchDetailKeywords(String searchSubCategory, String searchValue) {
//        if("title".equals(searchSubCategory)) {
//            if (StringUtils.hasLength(searchValue)) {
//                return bookEntity.title.contains(searchValue);
//            }
//        }
//        else if("author".equals(searchSubCategory)) {
//            if (StringUtils.hasLength(searchValue)) {
//                return bookEntity.author.contains(searchValue);
//            }
//        }
//        else if("publisher".equals(searchSubCategory)) {
//            if (StringUtils.hasLength(searchValue)) {
//                return bookEntity.publisher.contains(searchValue);
//            }
//        }
//        return null;
//    }

    // 제목 + 저자 + 출판사 통합 검색
    private BooleanExpression searchKeyword(String keyword) {

        if(!StringUtils.hasText(keyword))
            return null;

        return bookEntity.title.containsIgnoreCase(keyword)
                    .or(bookEntity.author.containsIgnoreCase(keyword))
                    .or(bookEntity.publisher.containsIgnoreCase(keyword));
    }

    private BooleanExpression searchMainKeywords(String searchMainCategory) {
        BookMainCategory category = Arrays.stream(BookMainCategory.values())
                .filter(c -> c.getCategoryCode().equals(searchMainCategory))
                .findFirst()
                .orElse(null);

        if (category != null) {
            return bookEntity.main_category.contains(category.getCategoryCode());
        }
        else {
            return null;
        }
    }

    private BooleanExpression searchSubKeywords(String searchSubCategory) {
        BookSubCategory category = Arrays.stream(BookSubCategory.values())
                .filter(c -> c.getCategoryCode().equals(searchSubCategory))
                .findFirst()
                .orElse(null);

        if(category != null) {
            return bookEntity.sub_category.contains(category.getCategoryCode());
        }
        else {
            return null;
        }
    }
}
