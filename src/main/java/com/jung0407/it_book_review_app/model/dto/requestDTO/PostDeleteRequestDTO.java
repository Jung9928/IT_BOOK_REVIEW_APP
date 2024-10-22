package com.jung0407.it_book_review_app.model.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostDeleteRequestDTO {

    private long postId;
    private String password;
}
