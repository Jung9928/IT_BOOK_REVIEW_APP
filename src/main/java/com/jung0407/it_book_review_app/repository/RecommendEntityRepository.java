package com.jung0407.it_book_review_app.repository;

import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import com.jung0407.it_book_review_app.model.entity.MemberEntity;
import com.jung0407.it_book_review_app.model.entity.RecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecommendEntityRepository extends JpaRepository<RecommendEntity, Long> {

    Optional<RecommendEntity> findByMemberAndGeneralForum(MemberEntity member, ForumEntity generalForumEntity);

    @Query(value = "SELECT COUNT(*) FROM RecommendEntity entity WHERE entity.generalForum =:generalForum")
    Long countByGeneralForum(@Param("generalForum") ForumEntity generalForum);

    List<RecommendEntity> findAllByGeneralForum(ForumEntity generalForumEntity);
}
