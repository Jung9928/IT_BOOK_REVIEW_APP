package com.jung0407.it_book_review_app.controller;

import com.jung0407.it_book_review_app.exception.ApplicationException;
import com.jung0407.it_book_review_app.exception.ErrorCode;
import com.jung0407.it_book_review_app.model.dto.requestDTO.*;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ForumPagingResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ForumResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.PostModifyResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ResponseResultCode;
import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import com.jung0407.it_book_review_app.repository.ForumRepository;
import com.jung0407.it_book_review_app.service.ForumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/forum")
@Slf4j
@RequiredArgsConstructor
public class ForumController {

    private final ForumService forumService;

    private ForumRepository forumRepository;

    // 게시글 조건부 전체 조회
    @GetMapping("/list")
    public ForumPagingResponseDTO<List<ForumResponseDTO>> getForumList(@PageableDefault(sort = {"registeredAt"}) Pageable pageable, ForumSearchConditionDTO forumSearchConditionDTO) throws Exception {

        log.info("searchCategory : " + forumSearchConditionDTO.getSearchCategory());
        log.info("searchValue : " + forumSearchConditionDTO.getSearchValue());

        return forumService.getForumBoardList(pageable, forumSearchConditionDTO);
    }

    // 게시글 작성
    @PostMapping("/post")
    public ResponseResultCode<Void> create(@RequestBody PostCreateDTO postCreateDTO) {
        log.info("title : " + postCreateDTO.getTitle());
        log.info("content : " + postCreateDTO.getContent());
        log.info("nickName : " + postCreateDTO.getNickName());
        log.info("password : " + postCreateDTO.getPassword());
        forumService.create(postCreateDTO.getTitle(), postCreateDTO.getContent(), postCreateDTO.getNickName(), postCreateDTO.getPassword());
        return ResponseResultCode.success();
    }


    // 게시글 수정
    @PutMapping("/modify")
    public ResponseResultCode<PostModifyResponseDTO> modify(@RequestBody PostModifyRequestDTO postModifyRequestDTO) {

        System.out.println("========================================");
        System.out.println("postId : " + postModifyRequestDTO.getPostId());
        System.out.println("title : " + postModifyRequestDTO.getTitle());
        System.out.println("content : " + postModifyRequestDTO.getContent());
        System.out.println("nickName : " + postModifyRequestDTO.getNickName());
        System.out.println("password : " + postModifyRequestDTO.getPassword());
        System.out.println("========================================");

        PostModifyResponseDTO postModifyResponseDTO = forumService.modify(postModifyRequestDTO);

        return ResponseResultCode.success(postModifyResponseDTO);
    }

    // 게시글 삭제
    @DeleteMapping("/delete")
    public ResponseResultCode<Void> delete(@RequestBody PostDeleteRequestDTO postDeleteRequestDTO) {

        log.info("postId : " + postDeleteRequestDTO.getPostId());
        log.info("password : " + postDeleteRequestDTO.getPassword());

        forumService.delete(postDeleteRequestDTO);
        return ResponseResultCode.success();
    }

    // 게시글 비밀번호 확인
    @GetMapping("/validate")
    public ResponseResultCode<Void> validate(@RequestParam Long postId, @RequestParam String password) {

        log.info("postId : " + postId);
        log.info("password : " + password);

        forumService.validate(postId, password);
        return ResponseResultCode.success();
    }

    // 게시글 단건 조회
    @GetMapping("/post/{postId}")
    public ResponseResultCode<ForumResponseDTO> getForumPostData(@PathVariable Long postId) {

        log.info("postId : " + postId);

        ForumResponseDTO forumResponseDTO = forumService.getForumPostData(postId);
        return ResponseResultCode.success(forumResponseDTO);
    }


}
