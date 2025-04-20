package com.jung0407.it_book_review_app.model.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostModifyRequestDTO {

    private Long postId;
    private String title;
    private String content;
    private String nickName;
    private String password;
}
