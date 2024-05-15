package com.jung0407.it_book_review_app.model.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForumPagingResponseDTO<T> {

    private LocalDateTime transactionTime;
    private String resultCode;
    private String description;
    private T data;
    private ForumPaginationDTO generalForumPaginationDTO;

    public static <T> ForumPagingResponseDTO<T> OK() {
        return (ForumPagingResponseDTO<T>) ForumPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    // DATA OK
    public static <T> ForumPagingResponseDTO<T> OK(T data) {
        return (ForumPagingResponseDTO<T>) ForumPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .build();
    }

    public static <T> ForumPagingResponseDTO<T> OK(T data, ForumPaginationDTO generalForumPaginationDTO) {
        return (ForumPagingResponseDTO<T>) ForumPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .generalForumPaginationDTO(generalForumPaginationDTO)
                .build();
    }

    public static <T> ForumPagingResponseDTO<T> ERROR(String description) {
        return (ForumPagingResponseDTO<T>) ForumPagingResponseDTO.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }
}
