package com.jung0407.it_book_review_app.service;

import com.jung0407.it_book_review_app.exception.ApplicationException;
import com.jung0407.it_book_review_app.exception.ErrorCode;
import com.jung0407.it_book_review_app.model.dto.requestDTO.CommentDeleteRequestDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.CommentRequestDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.ReplyDeleteRequestDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.ReplyRequestDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.CommentDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ReplyDTO;
import com.jung0407.it_book_review_app.model.entity.CommentEntity;
import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import com.jung0407.it_book_review_app.repository.CommentRepository;
import com.jung0407.it_book_review_app.repository.ForumRepository;
import com.jung0407.it_book_review_app.util.CommentDeleteStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final ForumRepository forumRepository;

    private final CommentRepository commentRepository;

    // 부모 댓글 & 작성일자 내림차순
    @Transactional
    public List<ReplyDTO> findCommentsByForum(Long postId) {
        forumRepository.findById(postId);
        return convertNestedStructure(commentRepository.findCommentByForum(postId));
    }


    @Transactional
    public Page<CommentDTO> pageList(ForumEntity forum, Pageable pageable) {
        Page<CommentEntity> commentEntities = commentRepository.findCommentEntityByForum(forum, pageable);
        return commentEntities.map(commentEntity -> CommentDTO.entityToCommentDTO(commentEntity));
    }

    @Transactional
    public CommentDTO createComment(CommentRequestDTO commentRequestDTO) {

        log.info("postId : " + commentRequestDTO.getPostId());

//        // 2. 회원 존재 여부 검증
//        MemberEntity memberEntity = memberRepository.findByMemberId(memberId).orElseThrow(() ->
//                new ApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s은 존재하지 않는 회원입니다. 회원가입 후 작성 바랍니다.", memberId)));

        //
        CommentEntity commentEntity = commentRepository.save(
                CommentEntity.getCommentEntity(
                        commentRequestDTO.getNickName(),
                        commentRequestDTO.getPassword(),
                        forumRepository.findById(commentRequestDTO.getPostId()).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", commentRequestDTO.getPostId()))),
                        commentRequestDTO.getParentId() != null ? commentRepository.findById(commentRequestDTO.getParentId()).orElseThrow(() -> new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, String.format("%s번 댓글을 찾을 수 없습니다.", commentRequestDTO.getParentId()))) : null,
                        commentRequestDTO.getContent()
                ));

        return CommentDTO.entityToCommentDTO(commentEntity);
    }

    @Transactional
    public ReplyDTO createReply(ReplyRequestDTO replyRequestDTO) {

        log.info("postId : " + replyRequestDTO.getPostId());

//        // 2. 회원 존재 여부 검증
//        MemberEntity memberEntity = memberRepository.findByMemberId(memberId).orElseThrow(() ->
//                new ApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s은 존재하지 않는 회원입니다. 회원가입 후 작성 바랍니다.", memberId)));

        //
        CommentEntity commentEntity = commentRepository.save(
                CommentEntity.getCommentEntity(
                        replyRequestDTO.getNickName(),
                        replyRequestDTO.getPassword(),
                        forumRepository.findById(replyRequestDTO.getPostId()).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", replyRequestDTO.getPostId()))),
                        replyRequestDTO.getParentId() != null ? commentRepository.findById(replyRequestDTO.getParentId()).orElseThrow(() -> new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, String.format("%s번 댓글을 찾을 수 없습니다.", replyRequestDTO.getParentId()))) : null,
                        replyRequestDTO.getReply()
                ));

        return ReplyDTO.entityToReplyDTO(commentEntity);
    }

    public long countComment(Long postId) {

        return commentRepository.countCommentEntityByPostId(postId);
    }

    // 댓글 삭제 (하위 대댓글도 같이 삭제)
    @Transactional
    public void deleteComment(CommentDeleteRequestDTO commentDeleteRequestDTO) {
        CommentEntity commentEntity = commentRepository.findCommentEntityByCommentIdWithParentCommentAndPassword(commentDeleteRequestDTO.getCommentId(), commentDeleteRequestDTO.getPassword());

        if (commentEntity == null) {
            // 예외 발생 또는 적절한 처리
            throw new IllegalArgumentException("잘못된 비밀번호 또는 댓글 ID 입니다.");
        }

        if(commentEntity.getChildComments().size() != 0) {
            commentEntity.changeCommentDeleteStatus(CommentDeleteStatus.Y);
        }
        else {
            commentRepository.delete(getDeletableAncestorComment(commentEntity));
        }
    }

    // 대댓글 삭제
    @Transactional
    public void deleteReply(ReplyDeleteRequestDTO replyDeleteRequestDTO) {
        CommentEntity commentEntity = commentRepository.findCommentEntityByCommentIdWithParentIdAndPassword(replyDeleteRequestDTO.getCommentId(), replyDeleteRequestDTO.getParentId(), replyDeleteRequestDTO.getPassword());

        commentRepository.delete(getDeletableAncestorComment(commentEntity));
    }

    private CommentEntity getDeletableAncestorComment(CommentEntity commentEntity) {
        CommentEntity parentComment = commentEntity.getParentComment();

        if(parentComment != null && parentComment.getChildComments().size() == 1 && parentComment.getIsDeleted() == CommentDeleteStatus.Y)
            return getDeletableAncestorComment(parentComment);

        return commentEntity;
    }

    private List<ReplyDTO> convertNestedStructure(List<CommentEntity> commentEntities) {
        List<ReplyDTO> result = new ArrayList<>();

        Map<Long, ReplyDTO> map = new HashMap<>();
        commentEntities.stream().forEach(c -> {
            ReplyDTO replyDTO = ReplyDTO.entityToReplyDTO(c);
            map.put(replyDTO.getId(), replyDTO);

            if(c.getParentComment() != null)
                map.get(c.getParentComment().getCommentId()).getChildComments().add(replyDTO);
            else
                result.add(replyDTO);
        });

        return result;
    }


//    @Transactional
//    public CommentDTO updateComment(CommentDTO commentDTO) {
//        CommentEntity commentEntity = commentRepository.findById(commentDTO.getId()).orElseThrow(() -> new ApplicationException(ErrorCode.BAD_REQUEST, "%s번 댓글을 찾을 수 없습니다."));
//        commentEntity.setComment(commentDTO.getComment());
//        commentRepository.updateComment(commentEntity);
//
//        return CommentDTO.entityToCommentDTO(commentEntity);
//    }
}
