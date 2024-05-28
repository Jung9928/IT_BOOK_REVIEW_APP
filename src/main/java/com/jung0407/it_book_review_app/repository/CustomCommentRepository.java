package com.jung0407.it_book_review_app.repository;

import com.jung0407.it_book_review_app.model.entity.CommentEntity;

import java.util.List;

public interface CustomCommentRepository {

    List<CommentEntity> findCommentByForum(Long postId);

    Long countCommentEntityByPostId(Long postId);

    void updateComment(CommentEntity commentEntity);
}
