package com.jung0407.it_book_review_app.model.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostValidateRequestDTO {

    private long postId;
    private String password;
}
