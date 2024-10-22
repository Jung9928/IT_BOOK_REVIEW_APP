package com.jung0407.it_book_review_app.model.dto.responseDTO;

import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class PostModifyResponseDTO {

    private Long postId;

    private String title;

    private byte[] content;

    private String nickName;

    private String password;

    private Timestamp registeredAt;

    private Timestamp modifiedAt;

    public static PostModifyResponseDTO getPostModifyResponseDTO(ForumEntity forumEntity) {
        return new PostModifyResponseDTO(
                forumEntity.getPostId(),
                forumEntity.getTitle(),
                forumEntity.getContent(),
                forumEntity.getNickName(),
                forumEntity.getPassword(),
                forumEntity.getRegisteredAt(),
                forumEntity.getModifiedAt()
        );
    }
}
