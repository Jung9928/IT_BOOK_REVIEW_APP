package com.jung0407.it_book_review_app.service;

import com.jung0407.it_book_review_app.configuration.CacheManagerConfig;
import com.jung0407.it_book_review_app.model.dto.BookPaginationDTO;
import com.jung0407.it_book_review_app.model.dto.BookSliceWithCount;
import com.jung0407.it_book_review_app.model.dto.requestDTO.BookSearchConditionDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.BookPagingResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.BookResponseDTO;
import com.jung0407.it_book_review_app.model.entity.BookEntity;
import com.jung0407.it_book_review_app.repository.BookRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepositoryCustom bookRepositoryCustom;
//    private final CacheManager cacheManager;

    public BookPagingResponseDTO<List<BookResponseDTO>> getBookList(Pageable pageable, BookSearchConditionDTO bookSearchConditionDTO) {

        // Slice<BookEntity> 조회
        BookSliceWithCount sliceWithCount = bookRepositoryCustom.findAllBySearchCondition(pageable, bookSearchConditionDTO);

        // Slice + DTO 리스트 변환
        List<BookResponseDTO> bookResponseDTOList = sliceWithCount.getContent().stream()
                .map(book -> BookResponseDTO.builder()
                        .book_id(book.getBook_id())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .publisher(book.getPublisher())
                        .publishDate(book.getPublishDate())
                        .rating(book.getRating())
                        .regular_price(book.getRegular_price())
                        .selling_price(book.getSelling_price())
                        .main_category(book.getMain_category())
                        .sub_category(book.getSub_category())
                        .imgPath(book.getImgPath())
                        .site(book.getSite())
                        .detailInfoPath(book.getDetailInfoPath())
                        .build())
                .collect(Collectors.toList());


        // 페이지네이션 DTO 구성
        BookPaginationDTO paginationDTO = BookPaginationDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalListCnt((int) sliceWithCount.getTotalCount())
                .hasNextPage(sliceWithCount.isHasNext())
                .build();

        return BookPagingResponseDTO.OK(bookResponseDTOList, paginationDTO);
    }

//    @Cacheable(value = CacheManagerConfig.CACHE_ID, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #bookSearchConditionDTO.toString()")
//    public BookPagingResponseDTO<List<BookResponseDTO>> getBookList(Pageable pageable, BookSearchConditionDTO bookSearchConditionDTO) {
//        List<BookResponseDTO> bookResponseDTOList = new ArrayList<>();
//
//        Page<BookEntity> bookEntities = bookRepositoryCustom.findAllBySearchCondition(pageable, bookSearchConditionDTO);
//
//        bookEntities.forEach(bookEntity -> bookResponseDTOList.add(BookResponseDTO.builder()
//                .book_id(bookEntity.getBook_id())
//                .isbn(bookEntity.getIsbn())
//                .title(bookEntity.getTitle())
//                .author(bookEntity.getAuthor())
//                .publisher(bookEntity.getPublisher())
//                .publishDate(bookEntity.getPublishDate())
//                .rating(bookEntity.getRating())
//                .regular_price(bookEntity.getRegular_price())
//                .selling_price(bookEntity.getSelling_price())
//                .main_category(bookEntity.getMain_category())
//                .sub_category(bookEntity.getSub_category())
//                .imgPath(bookEntity.getImgPath())
//                .modifiedAt(bookEntity.getModifiedAt())
//                .site(bookEntity.getSite())
//                .detailInfoPath(bookEntity.getDetailInfoPath())
//                .build()));
//
//        BookPaginationDTO bookPaginationDTO = new BookPaginationDTO(
//                (int) bookEntities.getTotalElements()
//                , pageable.getPageNumber()
//                , pageable.getPageSize()
//        );
//
//        return BookPagingResponseDTO.OK(bookResponseDTOList, bookPaginationDTO);
//    }
//
//    // 캐시 데이터 조회
//    public BookPagingResponseDTO<List<BookResponseDTO>> getCachedBooks(String key) {
//        Cache cache = cacheManager.getCache("books");
//
//        if(cache != null) {
//            return cache.get(key, BookPagingResponseDTO.class);
//        }
//
//        return null;
//    }
}
