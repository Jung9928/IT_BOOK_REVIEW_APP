package com.jung0407.it_book_review_app.model.dto.responseDTO;

import com.jung0407.it_book_review_app.model.dto.ReviewPaginationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewPagingResponseDTO<T> {

    private LocalDateTime transactionTime;
    private String resultCode;
    private String description;
    private T data;
    private ReviewPaginationDTO reviewPaginationDTO;

    public static <T> ReviewPagingResponseDTO<T> OK() {
        return (ReviewPagingResponseDTO<T>) ReviewPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    public static <T> ReviewPagingResponseDTO<T> OK(T data) {
        return (ReviewPagingResponseDTO<T>) ReviewPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .build();
    }

    public static <T> ReviewPagingResponseDTO<T> OK(T data, ReviewPaginationDTO reviewPaginationDTO) {
        return (ReviewPagingResponseDTO<T>) ReviewPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .reviewPaginationDTO(reviewPaginationDTO)
                .build();
    }

    public static <T> ReviewPagingResponseDTO<T> ERROR(String description) {
        return (ReviewPagingResponseDTO<T>) ReviewPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }
}
