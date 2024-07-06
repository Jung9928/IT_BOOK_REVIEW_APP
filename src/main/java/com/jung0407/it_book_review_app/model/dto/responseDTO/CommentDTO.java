package com.jung0407.it_book_review_app.model.dto.responseDTO;

import com.jung0407.it_book_review_app.model.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.jung0407.it_book_review_app.util.CommentDeleteStatus.Y;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String comment;
    private String nickName;

    private String password;

    private Long postId;

    private List<CommentDTO> childComments = new ArrayList<>();
    private String isDeleted;

    private Timestamp registeredAt;

    public CommentDTO(Long id, String comment, String nickName, String password, Long postId, String isDeleted, Timestamp registeredAt) {
        this.id = id;
        this.comment = comment;
        this.nickName = nickName;
        this.password = password;
        this.postId = postId;
        this.isDeleted = isDeleted;
        this.registeredAt = registeredAt;
    }

    public static CommentDTO entityToCommentDTO(CommentEntity commentEntity) {
        return commentEntity.getIsDeleted() == Y ?
                new CommentDTO(commentEntity.getCommentId(), "삭제된 댓글입니다.", null, null, null, Y.name(), null) :
                new CommentDTO(commentEntity.getCommentId(),  commentEntity.getComment(), commentEntity.getNickName(), commentEntity.getPassword(), commentEntity.getForum().getPostId(), commentEntity.getIsDeleted().name(), commentEntity.getRegisteredAt());
    }
}
