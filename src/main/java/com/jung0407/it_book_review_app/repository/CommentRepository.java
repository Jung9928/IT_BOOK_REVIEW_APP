package com.jung0407.it_book_review_app.repository;

import com.jung0407.it_book_review_app.model.entity.CommentEntity;
import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, CustomCommentRepository {

    @Query("select c  from CommentEntity c left join fetch c.parentComment where c.commentId = :commentId and c.password = :password")
    CommentEntity findCommentEntityByCommentIdWithParentCommentAndPassword(@Param("commentId") Long commentId, @Param("password") String password);

//    @Query("select c from CommentEntity c left join fetch c.parentComment where c.commentId = :commentId and c.parentComment = :parentId and c.password = :password")
//    CommentEntity findCommentEntityByCommentIdWithParentIdAndPassword(@Param("commentId") Long commentId, @Param("parentId") Long parentId, @Param("password") String password);

    @Query("select c from CommentEntity c left join fetch c.parentComment p where c.commentId = :commentId and p.commentId = :parentId and c.password = :password")
    CommentEntity findCommentEntityByCommentIdWithParentIdAndPassword(@Param("commentId") Long commentId, @Param("parentId") Long parentId, @Param("password") String password);

    Page<CommentEntity> findCommentEntityByForum(ForumEntity forum, Pageable pageable);

}
