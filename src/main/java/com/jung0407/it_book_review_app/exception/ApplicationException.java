package com.jung0407.it_book_review_app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    // 로그인 과정에서 비밀번호 검증 시, 따로 에러메세지가 필요 없어서 에러코드만 사용하기 위한 생성자 추가
//    public ApplicationException(ErrorCode errorCode) {
//        this.errorCode = errorCode;
//        this.message = null;
//    }

    @Override
    public String getMessage() {
        if(message == null) {
            return errorCode.getMessage();
        }

        return String.format("%s, %s", errorCode.getMessage(), message);
    }
}
