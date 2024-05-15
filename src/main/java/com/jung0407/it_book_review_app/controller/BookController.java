package com.jung0407.it_book_review_app.controller;

import com.jung0407.it_book_review_app.model.dto.requestDTO.BookSearchConditionDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.BookPagingResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.BookResponseDTO;
import com.jung0407.it_book_review_app.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    @GetMapping("/list")
    public BookPagingResponseDTO<List<BookResponseDTO>> getBookList(@PageableDefault(size = 9, sort = {"publishDate"}) Pageable pageable, BookSearchConditionDTO bookSearchConditionDTO) {
        log.info("searchMainCategory : " + bookSearchConditionDTO.getSearchMainCategory());
        log.info("searchSubCategory : " + bookSearchConditionDTO.getSearchSubCategory());
        log.info("searchDetailCategory : " + bookSearchConditionDTO.getSearchDetailCategory());
        log.info("searchValue : " + bookSearchConditionDTO.getSearchValue());
//        log.info("page : " + pageable.getPageNumber());
//        log.info("page offset : " + pageable.getOffset());
        return bookService.getBookList(pageable, bookSearchConditionDTO);
    }
}
