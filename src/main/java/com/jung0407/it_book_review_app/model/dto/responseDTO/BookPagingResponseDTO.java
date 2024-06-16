package com.jung0407.it_book_review_app.model.dto.responseDTO;

import com.jung0407.it_book_review_app.model.dto.BookPaginationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookPagingResponseDTO<T> {

    private LocalDateTime transactionTime;
    private String resultCode;
    private String description;
    private T data;
    private BookPaginationDTO bookPaginationDTO;

    public static <T> BookPagingResponseDTO<T> OK() {
        return (BookPagingResponseDTO<T>) BookPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    // DATA OK
    public static <T> BookPagingResponseDTO<T> OK(T data) {
        return (BookPagingResponseDTO<T>) BookPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .build();
    }

    public static <T> BookPagingResponseDTO<T> OK(T data, BookPaginationDTO bookPaginationDTO) {
        return (BookPagingResponseDTO<T>) BookPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .bookPaginationDTO(bookPaginationDTO)
                .build();
    }

    public static <T> BookPagingResponseDTO<T> ERROR(String description) {
        return (BookPagingResponseDTO<T>) BookPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }
}
