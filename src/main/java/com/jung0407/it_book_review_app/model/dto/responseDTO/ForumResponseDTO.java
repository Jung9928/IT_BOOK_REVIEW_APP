package com.jung0407.it_book_review_app.model.dto.responseDTO;

import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForumResponseDTO {

    private long post_id;

    private String member_id;

    private String title;

    private String content;

    private int vw_cnt;

    private int rcmnd_cnt;

    private Timestamp registeredAt;

    private Timestamp modifiedAt;

    // MemberEntity에 있는 필드들을 MemberDTO 클래스로 변환하는 메소드
    public static ForumResponseDTO entityToMemberDTO(ForumEntity forumEntity) {
        return new ForumResponseDTO(
                forumEntity.getPostId(),
                forumEntity.getMember().getMemberId(),
                forumEntity.getTitle(),
                forumEntity.getContent().toString(),
                forumEntity.getViewCount(),
                0,
                forumEntity.getRegisteredAt(),
                forumEntity.getModifiedAt()
        );
    }
}
