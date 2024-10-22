package com.jung0407.it_book_review_app.service;

import com.jung0407.it_book_review_app.exception.ApplicationException;
import com.jung0407.it_book_review_app.exception.ErrorCode;
import com.jung0407.it_book_review_app.model.dto.requestDTO.ForumSearchConditionDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.PostDeleteRequestDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.PostModifyRequestDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.PostValidateRequestDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ForumPaginationDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ForumPagingResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ForumResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.PostModifyResponseDTO;
import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import com.jung0407.it_book_review_app.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForumService {

    private final ForumRepositoryCustom forumRepositoryCustom;

    private final ForumRepository forumRepository;

    private final RecommendEntityRepository recommendEntityRepository;

    // 게시글 목록 조회
    public ForumPagingResponseDTO<List<ForumResponseDTO>> getForumBoardList(Pageable pageable, ForumSearchConditionDTO forumSearchConditionDTO) {

        List<ForumResponseDTO> forumResponseDTOList = new ArrayList<>();

        Page<ForumEntity> forumEntities = forumRepositoryCustom.findAllBySearchCondition(pageable, forumSearchConditionDTO);

        for (ForumEntity forumEntity : forumEntities) {
            ForumResponseDTO forumResponseDTO = ForumResponseDTO.builder()
                    .post_id(forumEntity.getPostId())
//                    .content(generalForumEntity.getContent())
                    .title(forumEntity.getTitle())
                    .nickName(forumEntity.getNickName())
                    .vw_cnt(forumEntity.getViewCount())
                    .registeredAt(forumEntity.getRegisteredAt())
                    .modifiedAt(forumEntity.getModifiedAt())
                    .build();

            forumResponseDTOList.add(forumResponseDTO);
        }

        ForumPaginationDTO forumPaginationDTO = new ForumPaginationDTO(
                (int)forumEntities.getTotalElements()
                , pageable.getPageNumber() + 1
                , pageable.getPageSize()
                , 10
        );

        return ForumPagingResponseDTO.OK(forumResponseDTOList, forumPaginationDTO);
    }

    // 게시글 상세 조회
    public ForumResponseDTO getForumPostData(long postId) {

        ForumResponseDTO forumResponseDTO = new ForumResponseDTO();

        // 게시글이 존재하는 지
        ForumEntity forumEntity = forumRepository.findById(postId).orElseThrow(() ->
                new ApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", postId)));

        // 게시글 조회수 증가
        increaseViewCount(postId);

        log.info("postId : " + forumEntity.getPostId());
        log.info("title : " + forumEntity.getTitle());
        log.info("content : " + forumEntity.getContent());
        log.info("nickName: " + forumEntity.getNickName());
        log.info("viewCount : " + forumEntity.getViewCount());
        log.info("registeredAt : " + forumEntity.getRegisteredAt());
        log.info("modifiedAt : " + forumEntity.getModifiedAt());

        forumResponseDTO.setPost_id(forumEntity.getPostId());
        forumResponseDTO.setTitle(forumEntity.getTitle());
        forumResponseDTO.setContent(new String(forumEntity.getContent(), StandardCharsets.UTF_8));
        forumResponseDTO.setNickName(forumEntity.getNickName());
        forumResponseDTO.setVw_cnt(forumEntity.getViewCount());
        forumResponseDTO.setRegisteredAt(forumEntity.getRegisteredAt());
        forumResponseDTO.setModifiedAt(forumEntity.getModifiedAt());

        return forumResponseDTO;
    }

    // 게시글 조회수 증가
    @Transactional
    public void increaseViewCount(Long postId) {
        forumRepository.countUpView(postId);
    }

    // 게시글 작성
    @Transactional
    public void create(String title, String content, String nickName, String password) {

        // 게시글 저장
        forumRepository.save(ForumEntity.getForumEntity(title, content.getBytes(StandardCharsets.UTF_8), nickName, password));
    }

    // 게시글 수정
    @Transactional
    public PostModifyResponseDTO modify(PostModifyRequestDTO postModifyRequestDTO) {

        // 게시글이 존재하는 지
        ForumEntity forumEntity = forumRepository.findById(postModifyRequestDTO.getPostId()).orElseThrow(() ->
                new ApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", postModifyRequestDTO.getPostId())));

        forumEntity.setTitle(postModifyRequestDTO.getTitle());
        forumEntity.setContent(postModifyRequestDTO.getContent().getBytes(StandardCharsets.UTF_8));
        forumEntity.setNickName(postModifyRequestDTO.getNickName());
        forumEntity.setPassword(postModifyRequestDTO.getPassword());

        return PostModifyResponseDTO.getPostModifyResponseDTO(forumRepository.save(forumEntity));
    }

    // 게시글 삭제
    @Transactional
    public void delete(PostDeleteRequestDTO postDeleteRequestDTO) {

        // 게시글이 존재하는 지
       ForumEntity forumEntity = forumRepository.findById(postDeleteRequestDTO.getPostId()).orElseThrow(() ->
                new ApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", postDeleteRequestDTO.getPostId())));

        // 게시글 삭제 권한 (비밀번호) 체크
        if (!forumEntity.getPassword().equals(postDeleteRequestDTO.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALID_POST_PERMISSION, String.format("해당 게시글의 비밀번호가 일치하지 않습니다."));
        }

        forumRepository.delete(forumEntity);
    }

    // 게시글 비밀번호 검증
    @Transactional
    public void validate(Long postId, String password) {

        // 게시글이 존재하는 지
        ForumEntity forumEntity = forumRepository.findById(postId).orElseThrow(() ->
                new ApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", postId)));

        // 게시글 수정 권한 (게시글을 작성한 사람인지) 체크
        if (!forumEntity.getPassword().equals(password)) {
            throw new ApplicationException(ErrorCode.INVALID_POST_PERMISSION, String.format("해당 게시글의 작성자가 아니므로 수정할 수 없습니다."));
        }
    }
}
