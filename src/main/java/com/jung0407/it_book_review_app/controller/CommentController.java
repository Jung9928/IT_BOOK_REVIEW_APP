package com.jung0407.it_book_review_app.controller;

import com.jung0407.it_book_review_app.model.dto.requestDTO.CommentDeleteRequestDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.CommentRequestDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.ReplyDeleteRequestDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.ReplyRequestDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.CommentDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ReplyDTO;
import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import com.jung0407.it_book_review_app.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    // 게시글에 (대)댓글 작성
    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentRequestDTO commentRequestDTO) {

//        MemberDTO memberDTO = memberService.getMemberByMemberId(customMemberDetails.getUsername());

        log.info("content : " + commentRequestDTO.getContent());
        log.info("postId : " + commentRequestDTO.getPostId());
        log.info("nickName : " + commentRequestDTO.getNickName());
        log.info("password : " + commentRequestDTO.getPassword());
        log.info("parentId : " + commentRequestDTO.getParentId());
        log.info("isDeleted : " + commentRequestDTO.getIsDeleted());

        CommentDTO commentDTO = commentService.createComment(commentRequestDTO);
        return ResponseEntity.ok().body(commentDTO);
    }

    // 게시글에 (대)댓글 작성
    @PostMapping("/reply/create")
    public ResponseEntity<ReplyDTO> createReply(@RequestBody ReplyRequestDTO replyRequestDTO) {

//        MemberDTO memberDTO = memberService.getMemberByMemberId(customMemberDetails.getUsername());

        log.info("content : " + replyRequestDTO.getReply());
        log.info("postId : " + replyRequestDTO.getPostId());
        log.info("nickName : " + replyRequestDTO.getNickName());
        log.info("password : " + replyRequestDTO.getPassword());
        log.info("parentId : " + replyRequestDTO.getParentId());
        log.info("isDeleted : " + replyRequestDTO.getIsDeleted());

        ReplyDTO replyDTO = commentService.createReply(replyRequestDTO);
        return ResponseEntity.ok().body(replyDTO);
    }

    // 게시글의 댓글 페이징
    @GetMapping("/list")
    public ResponseEntity<Page<CommentDTO>> commentList(@RequestParam ForumEntity forumEntity, @PageableDefault(size = 10, sort = "commentId", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("postId : " + forumEntity.getPostId());
        log.info("title : " + forumEntity.getTitle());
        log.info("nickName : " + forumEntity.getNickName());
        log.info("content : " + forumEntity.getContent());
        log.info("comments : " + forumEntity.getComments());


        Page<CommentDTO> commentDTOS = commentService.pageList(forumEntity, pageable);

        return ResponseEntity.ok().body(commentDTOS);
    }

//    // 게시글 댓글의 대댓글 페이징
//    @GetMapping("/reply/{postId}")
//    public ResponseEntity<List<ReplyDTO>> replyList(@PathVariable Long postId) {
//        log.info("postId : " + postId);
//        List<ReplyDTO> replyDTOS = commentService.findCommentsByForum(postId);
//        return ResponseEntity.ok().body(replyDTOS);
//    }

    // 게시글 댓글의 대댓글 리스트
    @GetMapping("/reply/{postId}")
    public ResponseEntity<List<ReplyDTO>> replyList(@PathVariable Long postId) {
        log.info("postId : " + postId);
        List<ReplyDTO> replyDTOS = commentService.findCommentsByForum(postId);
        return ResponseEntity.ok().body(replyDTOS);
    }

    // 게시글 카운팅
    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> countCommentEntityByPostId(@PathVariable Long postId) {
        log.info("postId : " + postId);
        long count = commentService.countComment(postId);
        log.info("count : " + count);
        return ResponseEntity.ok(count);
    }

    // 댓글 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<CommentDTO> deleteComment(@RequestBody CommentDeleteRequestDTO commentDeleteRequestDTO) {
        log.info("commentId : " + commentDeleteRequestDTO.getCommentId());
        log.info("password : " + commentDeleteRequestDTO.getPassword());

        commentService.deleteComment(commentDeleteRequestDTO);
        return ResponseEntity.noContent().build();
    }

    // 대댓글 삭제
    @DeleteMapping("/reply/delete")
    public ResponseEntity<CommentDTO> deleteReply(@RequestBody ReplyDeleteRequestDTO replyDeleteRequestDTO) {

        log.info("parentId : " + replyDeleteRequestDTO.getParentId());
        log.info("commentId : " + replyDeleteRequestDTO.getCommentId());
        log.info("password : " + replyDeleteRequestDTO.getPassword());

        commentService.deleteReply(replyDeleteRequestDTO);
        return ResponseEntity.noContent().build();
    }

//    // 댓글 수정
//    @PutMapping("/modify")
//    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentCreateRequestDto) {
//        CommentDTO commentDTO = commentService.updateComment(commentCreateRequestDto);
//        return ResponseEntity.ok().body(commentDTO);
//    }
}
