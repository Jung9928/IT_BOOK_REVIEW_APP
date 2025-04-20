package com.jung0407.it_book_review_app.model.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostCreateDTO {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @NotBlank(message = "닉네임을 입력하세요.")
    private String nickName;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}
