package com.jung0407.it_book_review_app.controller;

import com.jung0407.it_book_review_app.model.dto.requestDTO.ForumSearchConditionDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ForumPagingResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ForumResponseDTO;
import com.jung0407.it_book_review_app.repository.ForumRepository;
import com.jung0407.it_book_review_app.service.ForumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/forum")
@Slf4j
@RequiredArgsConstructor
public class ForumController {

    private final ForumService forumService;

    private ForumRepository forumRepository;

    @GetMapping("/list")
    public ForumPagingResponseDTO<List<ForumResponseDTO>> getForumList(@PageableDefault(sort = {"registeredAt"}) Pageable pageable, ForumSearchConditionDTO forumSearchConditionDTO) throws Exception {

        log.info("searchCategory : " + forumSearchConditionDTO.getSearchCategory());
        log.info("searchValue : " + forumSearchConditionDTO.getSearchValue());

        return forumService.getForumBoardList(pageable, forumSearchConditionDTO);
    }
}
