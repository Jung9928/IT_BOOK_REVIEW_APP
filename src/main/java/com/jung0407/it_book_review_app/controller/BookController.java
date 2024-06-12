package com.jung0407.it_book_review_app.controller;

import com.jung0407.it_book_review_app.model.dto.requestDTO.BookSearchConditionDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.BookPagingResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.BookResponseDTO;
import com.jung0407.it_book_review_app.model.entity.BookEntity;
import com.jung0407.it_book_review_app.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    @Cacheable(cacheNames = "books")
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


//    @Cacheable(cacheNames = "books")
//    @GetMapping("/list")
//    public List<BookEntity> getBookList(BookSearchConditionDTO bookSearchConditionDTO) {
//        log.info("searchMainCategory : " + bookSearchConditionDTO.getSearchMainCategory());
//        log.info("searchSubCategory : " + bookSearchConditionDTO.getSearchSubCategory());
//        log.info("searchDetailCategory : " + bookSearchConditionDTO.getSearchDetailCategory());
//        log.info("searchValue : " + bookSearchConditionDTO.getSearchValue());
////        log.info("page : " + pageable.getPageNumber());
////        log.info("page offset : " + pageable.getOffset());
//        return bookService.getAllBookList(bookSearchConditionDTO);
//    }


    // 캐시된 데이터 조회
    @GetMapping("/cached/list")
    public BookPagingResponseDTO<List<BookResponseDTO>> getCachedBooks(@RequestParam String key) {
        log.info("캐시 조회 시작\n");
        BookPagingResponseDTO<List<BookResponseDTO>> cachedBooks = bookService.getCachedBooks(key);
        if (cachedBooks != null) {
            log.info("캐시 조회 성공: " + cachedBooks.getData().size() + "개의 데이터");
        } else {
            log.info("캐시 조회 실패: 데이터 없음");
        }
        return cachedBooks;
    }
}
