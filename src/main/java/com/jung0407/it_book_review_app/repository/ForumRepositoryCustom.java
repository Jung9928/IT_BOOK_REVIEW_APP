package com.jung0407.it_book_review_app.repository;

import com.jung0407.it_book_review_app.model.dto.requestDTO.ForumSearchConditionDTO;
import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.jung0407.it_book_review_app.model.entity.QForumEntity.forumEntity;
import static com.jung0407.it_book_review_app.model.entity.QRecommendEntity.recommendEntity;

@RequiredArgsConstructor
@Repository
public class ForumRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<ForumEntity> findAllBySearchCondition(Pageable pageable, ForumSearchConditionDTO generalForumSearchConditionDTO) {

        JPAQuery<ForumEntity> query =
                queryFactory.selectFrom(forumEntity).where(
                        searchKeywords(generalForumSearchConditionDTO.getSearchCategory(), generalForumSearchConditionDTO.getSearchValue())
                );


        long total = query.stream().count();

        List<ForumEntity> results = query
                .where(
                        searchKeywords(generalForumSearchConditionDTO.getSearchCategory(), generalForumSearchConditionDTO.getSearchValue()))
                .offset((long)(pageable.getPageNumber()-1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(forumEntity.registeredAt.desc())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression searchKeywords(String searchCategory, String searchValue) {
        if("title".equals(searchCategory)) {
            if(StringUtils.hasLength(searchValue)) {
                return forumEntity.title.contains(searchValue);
            }
        }
        else if("author".equals(searchCategory)) {
            if(StringUtils.hasLength(searchValue)) {
                return forumEntity.nickName.contains(searchValue);
            }
        }
        else if("content".equals(searchCategory)) {
            if(StringUtils.hasLength(searchCategory)) {
                return forumEntity.content.in(searchValue.getBytes(StandardCharsets.UTF_8));
            }
        }

        return null;
    }
}
