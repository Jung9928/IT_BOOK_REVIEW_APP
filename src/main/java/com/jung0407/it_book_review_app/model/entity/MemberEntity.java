package com.jung0407.it_book_review_app.model.entity;

import com.jung0407.it_book_review_app.util.MemberRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String memberId;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 50, unique = true)
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole = MemberRole.ROLE_USER;

    @Column(name = "reg_at")
    private Timestamp registeredAt;

    @Column(name = "mod_at")
    private Timestamp modifiedAt;

    @PrePersist
    void regDtm() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void modDtm() {
        this.modifiedAt = Timestamp.from(Instant.now());
    }

//    public static MemberEntity toMemberEntity(MemberJoinDTO memberJoinDTO) {
//        MemberEntity memberEntity = new MemberEntity();
//
//        memberEntity.setId(memberJoinDTO.getId());
//        memberEntity.setMemberId(memberJoinDTO.getMemberId());
//        memberEntity.setPassword(memberJoinDTO.getPassword());
//        memberEntity.setEmail(memberJoinDTO.getEmail());
//
//        return memberEntity;
//    }

    public static MemberEntity createMemberEntity(String memberId, String password, String email, MemberRole memberRole) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMemberId(memberId);
        memberEntity.setPassword(password);
        memberEntity.setEmail(email);
        memberEntity.setMemberRole(memberRole.ROLE_USER);

        return memberEntity;
    }
//
//    @OneToMany(mappedBy = "member")
//    private List<PostEntity> posts;
}
