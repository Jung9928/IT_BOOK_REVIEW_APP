package com.jung0407.it_book_review_app.service;

import com.jung0407.it_book_review_app.model.dto.BookPaginationDTO;
import com.jung0407.it_book_review_app.model.dto.requestDTO.BookSearchConditionDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.BookPagingResponseDTO;
import com.jung0407.it_book_review_app.model.dto.responseDTO.BookResponseDTO;
import com.jung0407.it_book_review_app.model.entity.BookEntity;
import com.jung0407.it_book_review_app.repository.BookRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepositoryCustom bookRepositoryCustom;


    public BookPagingResponseDTO<List<BookResponseDTO>> getBookList(Pageable pageable, BookSearchConditionDTO bookSearchConditionDTO) {
        List<BookResponseDTO> bookResponseDTOList = new ArrayList<>();

        Page<BookEntity> bookEntities = bookRepositoryCustom.findAllBySearchCondition(pageable, bookSearchConditionDTO);

        bookEntities.forEach(bookEntity -> bookResponseDTOList.add(BookResponseDTO.builder()
                    .book_id(bookEntity.getBook_id())
                    .isbn(bookEntity.getIsbn())
                    .title(bookEntity.getTitle())
                    .author(bookEntity.getAuthor())
                    .publisher(bookEntity.getPublisher())
                    .publishDate(bookEntity.getPublishDate())
                    .rating(bookEntity.getRating())
                    .regular_price(bookEntity.getRegular_price())
                    .selling_price(bookEntity.getSelling_price())
                    .main_category(bookEntity.getMain_category())
                    .sub_category(bookEntity.getSub_category())
                    .imgPath(bookEntity.getImgPath())
                    .modifiedAt(bookEntity.getModifiedAt())
                    .site(bookEntity.getSite())
                    .detailInfoPath(bookEntity.getDetailInfoPath())
                    .build()));

        BookPaginationDTO bookPaginationDTO = new BookPaginationDTO(
                (int) bookEntities.getTotalElements()
                , pageable.getPageNumber()
                , pageable.getPageSize()
                , 9
        );

        return BookPagingResponseDTO.OK(bookResponseDTOList, bookPaginationDTO);
    }
}
