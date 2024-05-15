package com.jung0407.it_book_review_app.repository;

import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ForumRepository extends JpaRepository<ForumEntity, Long> {

    Optional<ForumEntity> findGeneralForumViewCountByPostId(long postId);

    Page<ForumEntity> findGeneralForumEntitiesByMemberId(long id, Pageable pageable);

    @Transactional
    @Modifying
    @Query("Update ForumEntity b set b.viewCount = b.viewCount +1 where b.postId = ?1")
    void countUpView(Long postId);
}
