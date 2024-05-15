package com.jung0407.it_book_review_app.service;

import com.jung0407.it_book_review_app.model.dto.requestDTO.ForumSearchConditionDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ForumPaginationDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ForumPagingResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ForumResponseDTO;
import com.jung0407.it_book_review_app.model.entity.ForumEntity;
import com.jung0407.it_book_review_app.model.vo.ForumVO;
import com.jung0407.it_book_review_app.model.vo.ReplyVO;
import com.jung0407.it_book_review_app.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForumService {

    private final ForumRepositoryCustom forumRepositoryCustom;

    private final ForumRepository generalForumRepository;
    private final MemberRepository memberRepository;

    private final RecommendEntityRepository recommendEntityRepository;

    // 게시글 목록 조회
    public ForumPagingResponseDTO<List<ForumResponseDTO>> getForumBoardList(Pageable pageable, ForumSearchConditionDTO forumSearchConditionDTO) {

        List<ForumResponseDTO> generalForumResponseDTOList = new ArrayList<>();

        Page<ForumEntity> generalForumEntities = forumRepositoryCustom.findAllBySearchCondition(pageable, forumSearchConditionDTO);

        for (ForumEntity generalForumEntity : generalForumEntities) {
            ForumResponseDTO generalForumResponseDTO = ForumResponseDTO.builder()
                    .member_id(generalForumEntity.getMember().getMemberId())
                    .post_id(generalForumEntity.getPostId())
//                    .content(generalForumEntity.getContent())
                    .title(generalForumEntity.getTitle())
                    .vw_cnt(generalForumEntity.getViewCount())
                    .registeredAt(generalForumEntity.getRegisteredAt())
                    .modifiedAt(generalForumEntity.getModifiedAt())
                    .build();

            generalForumResponseDTOList.add(generalForumResponseDTO);
        }

        ForumPaginationDTO generalForumPaginationDTO = new ForumPaginationDTO(
                (int)generalForumEntities.getTotalElements()
                , pageable.getPageNumber() + 1
                , pageable.getPageSize()
                , 10
        );

        return ForumPagingResponseDTO.OK(generalForumResponseDTOList, generalForumPaginationDTO);
    }

    // 게시글 단건 조회
//    public ForumResponseDTO getForumPostData(long postId) {
//
//        ForumResponseDTO generalForumResponseDTO = new ForumResponseDTO();
//
//        // 게시글이 존재하는 지
//        ForumEntity generalForumEntity = generalForumRepository.findById(postId).orElseThrow(() ->
//                new ApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", postId)));
//
//        log.info("postId : " + generalForumEntity.getPostId());
//        log.info("memberId : " + generalForumEntity.getMember().getMemberId());
//        log.info("title : " + generalForumEntity.getTitle());
//        log.info("content : " + generalForumEntity.getContent());
//        log.info("viewCount : " + generalForumEntity.getViewCount());
//        log.info("registeredAt : " + generalForumEntity.getRegisteredAt());
//        log.info("modifiedAt : " + generalForumEntity.getModifiedAt());
//
//        generalForumResponseDTO.setPost_id(generalForumEntity.getPostId());
//        generalForumResponseDTO.setMember_id(generalForumEntity.getMember().getMemberId());
//        generalForumResponseDTO.setTitle(generalForumEntity.getTitle());
//        generalForumResponseDTO.setContent(new String(generalForumEntity.getContent(), StandardCharsets.UTF_8));
//        generalForumResponseDTO.setVw_cnt(generalForumEntity.getViewCount());
//        generalForumResponseDTO.setRegisteredAt(generalForumEntity.getRegisteredAt());
//        generalForumResponseDTO.setModifiedAt(generalForumEntity.getModifiedAt());
//
//        return generalForumResponseDTO;
//    }
}
