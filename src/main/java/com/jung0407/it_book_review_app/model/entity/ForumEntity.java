package com.jung0407.it_book_review_app.model.entity;

import jakarta.persistence.*;
//import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "forum")
public class ForumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "nickName", nullable = false)
    private String nickName;

    @Column(name = "password", nullable = false)
    private String password;

//    @Size(min = 2, max=40, message = "제목은 2자이상 40자 이하입니다.")
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "BLOB", nullable = false)
    private byte[] content;

    @Column(name = "vw_cnt", nullable = false)
    @ColumnDefault("0")
    private int viewCount;

    @OneToMany(mappedBy = "forum", orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

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

    public static ForumEntity getForumEntity(String title, byte[] content, String nickName, String password) {
        ForumEntity forumEntity = new ForumEntity();
        forumEntity.setTitle(title);
        forumEntity.setContent(content);
        forumEntity.setNickName(nickName);
        forumEntity.setPassword(password);

        return forumEntity;
    }

    public ForumEntity(String title, byte[] content, String nickName, String password) {
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        this.password = password;
    }

    // 수정이 필요할 경우
    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    // 게시글 수정 시 호출
    public void updateContent(String title, byte[] content) {
        this.title = title;
        this.content = content;

        // 수정일 갱신
        modifiedAt();
    }

    // 게시글 조회 시 호출
    public void increaseViewCount() {
        this.viewCount++;
    }
}
