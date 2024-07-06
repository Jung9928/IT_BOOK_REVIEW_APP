package com.jung0407.it_book_review_app.model.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ResponseResultCode<T> {

    private String resultCode;
    private T result;
    private ResponseEntity response;

    // 어떠한 작업을 실패할 경우
    public static ResponseResultCode<Void> error(String errorCode) {
        return new ResponseResultCode<>(errorCode, null, null);
    }

    // 어떠한 작업을 성공할 경우 (회원가입, 게시글 작성, 삭제, 수정 등)
    // 각각 결과값이 다른 타입을 가지게끔 설계할 것이므로 제네릭(T) 타입 사용하여 다양한 반환 값을 처리하도록 유연성 높임.
    public static <T> ResponseResultCode<T> success(T result) {
        return new ResponseResultCode<>("SUCCESS", result, ResponseEntity.ok("로그인 성공"));
    }


    public static <T> ResponseResultCode<T> success() {
        return new ResponseResultCode<>("SUCCESS", null, null);
    }
}
