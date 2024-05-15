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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "forum")
public class ForumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

//    @Size(min = 2, max=40, message = "제목은 2자이상 40자 이하입니다.")
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "BLOB", nullable = false)
    private byte[] content;

    @Column(name = "vw_cnt", nullable = false)
    @ColumnDefault("0")
    private int viewCount;

    @OneToMany(mappedBy = "postId", orphanRemoval = true)
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

    public static ForumEntity getForumEntity(String title, byte[] content, MemberEntity memberId) {
        ForumEntity entity = new ForumEntity();
        entity.setTitle(title);
        entity.setContent(content);
        entity.setMember(memberId);
        return entity;
    }
}
