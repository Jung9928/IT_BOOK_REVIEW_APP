package com.jung0407.it_book_review_app.model.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequestDTO {

    private String reply;
    private Long postId;
    private String nickName;
    private String password;
    private Long parentId;
    private String isDeleted;
}
