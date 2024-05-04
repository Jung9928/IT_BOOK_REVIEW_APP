package com.jung0407.it_book_review_app.repository;

import com.jung0407.it_book_review_app.model.dto.requestDTO.BookSearchConditionDTO;
import com.jung0407.it_book_review_app.model.entity.BookEntity;
import com.jung0407.it_book_review_app.util.BookMainCategory;
import com.jung0407.it_book_review_app.util.BookSubCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static com.jung0407.it_book_review_app.model.entity.QBookEntity.bookEntity;

// QueryDSL을 사용하므로 인터페이스가 아닌 클래스를 생성하여 JPAQueryFactory을 통해 구현
@RequiredArgsConstructor
@Repository
public class BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<BookEntity> findAllBySearchCondition(Pageable pageable, BookSearchConditionDTO bookSearchConditionDTO) {
        JPAQuery<BookEntity> query =
                queryFactory.selectFrom(bookEntity).where(
                        searchMainKeywords(bookSearchConditionDTO.getSearchMainCategory()),
                        searchSubKeywords(bookSearchConditionDTO.getSearchSubCategory()),
                        searchDetailKeywords(bookSearchConditionDTO.getSearchDetailCategory(), bookSearchConditionDTO.getSearchValue())
                );

        long total = query.stream().count();        // 전체 도서 데이터 카운트 후, 아래에서 조건 처리

        List<BookEntity> results = query
                // 페이지 번호
                // 프론트에서 맨 처음 로딩 시, 1페이지 값을 전달하므로 getPageNumber에 -1하여 offset 첫 값을 0설정
                // ex) limit(0, 9) -> limit(9, 18) -> limit(18, 27)
                .offset((long)(pageable.getPageNumber()-1) * pageable.getPageSize())
                .limit(pageable.getPageSize())      // 페이지에 표시할 도서 갯수
                .orderBy(bookEntity.publishDate.desc())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression searchDetailKeywords(String searchSubCategory, String searchValue) {
        if("title".equals(searchSubCategory)) {
            if (StringUtils.hasLength(searchValue)) {
                return bookEntity.title.contains(searchValue);
            }
        }
        else if("author".equals(searchSubCategory)) {
            if (StringUtils.hasLength(searchValue)) {
                return bookEntity.author.contains(searchValue);
            }
        }
        else if("publisher".equals(searchSubCategory)) {
            if (StringUtils.hasLength(searchValue)) {
                return bookEntity.publisher.contains(searchValue);
            }
        }
        return null;
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
