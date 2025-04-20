package com.jung0407.it_book_review_app.repository;

import com.jung0407.it_book_review_app.model.entity.CommentEntity;
import com.jung0407.it_book_review_app.model.entity.QCommentEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.jung0407.it_book_review_app.model.entity.QCommentEntity.commentEntity;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentEntity> findCommentByForum(Long postId) {
        return queryFactory.selectFrom(commentEntity)
                .leftJoin(commentEntity.parentComment)
                .fetchJoin()
                .where(commentEntity.forum.postId.eq(postId))
                .orderBy(commentEntity.parentComment.commentId.asc().nullsFirst())
                .fetch();
    }

    @Override
    public Long countCommentEntityByPostId(Long postId) {
        return queryFactory.select(commentEntity.count())
                .from(commentEntity)
                .where(commentEntity.forum.postId.eq(postId))
                .fetchOne();
    }

    @Override
    public void updateComment(CommentEntity commentEntity) {
        queryFactory.update(QCommentEntity.commentEntity)
                .where(QCommentEntity.commentEntity.commentId.eq(commentEntity.getCommentId()))         // 댓글 ID로 조건 설정
                .set(QCommentEntity.commentEntity.comment, commentEntity.getComment())                  // 댓글 내용 업데이트
                .execute();
    }
}
