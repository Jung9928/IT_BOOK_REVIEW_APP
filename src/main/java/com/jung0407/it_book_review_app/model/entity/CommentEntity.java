package com.jung0407.it_book_review_app.model.entity;

import com.jung0407.it_book_review_app.util.CommentDeleteStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "anonymous_comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private ForumEntity forum;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column(nullable = false)
    @Lob
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommentEntity parentComment;

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private List<CommentEntity> childComments = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private CommentDeleteStatus isDeleted;

    @Column(name = "reg_at")
    private Timestamp registeredAt;

    @Column(name = "mod_at")
    private Timestamp modifiedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void modifiedAt() {
        this.modifiedAt = Timestamp.from(Instant.now());
    }

    public static CommentEntity getCommentEntity(String nickName, String password, ForumEntity postId, CommentEntity parentComment, String comment) {
        CommentEntity entity = new CommentEntity();

        entity.setNickName(nickName);
        entity.setPassword(password);
        entity.setForum(postId);
        entity.setComment(comment);
        entity.setParentComment(parentComment);
        entity.setIsDeleted(CommentDeleteStatus.N);

        return entity;
    }

    public void changeCommentDeleteStatus(CommentDeleteStatus deleteStatus) {
        this.isDeleted = deleteStatus;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
