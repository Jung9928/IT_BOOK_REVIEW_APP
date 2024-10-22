package com.jung0407.it_book_review_app.model.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDeleteRequestDTO {

    private Long commentId;
    private String password;
}
