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
public class ReplyDTO {

    private Long id;

    private String reply;
    private String nickName;

    private String password;

    private Long postId;

    private List<ReplyDTO> childComments = new ArrayList<>();
    private String isDeleted;

    private Timestamp registeredAt;

    public ReplyDTO(Long id, String reply, String nickName, String password, Long postId, String isDeleted, Timestamp registeredAt) {
        this.id = id;
        this.reply = reply;
        this.nickName = nickName;
        this.password = password;
        this.postId = postId;
        this.isDeleted = isDeleted;
        this.registeredAt = registeredAt;
    }

    public static ReplyDTO entityToReplyDTO(CommentEntity commentEntity) {
        return commentEntity.getIsDeleted() == Y ?
                new ReplyDTO(commentEntity.getCommentId(), "삭제된 댓글입니다.", null, null, null, Y.name(), null) :
                new ReplyDTO(commentEntity.getCommentId(), commentEntity.getComment(), commentEntity.getNickName(), commentEntity.getPassword(), commentEntity.getForum().getPostId(), commentEntity.getIsDeleted().name(), commentEntity.getRegisteredAt());
    }
}
