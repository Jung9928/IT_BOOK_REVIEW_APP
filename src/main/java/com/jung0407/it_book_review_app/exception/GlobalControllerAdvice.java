package com.jung0407.it_book_review_app.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jung0407.it_book_review_app.model.dto.responseDTO.ResponseResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> applicationHandler(ApplicationException e) {
        log.error("오류 발생 {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseResultCode.error(e.getErrorCode().name()));
    }

    // 어플리케이션 Runtime 중에 발생하는 예외(db crud 등등)를 핸들링하는 함수
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> applicationHandler(RuntimeException e) {
        log.error("오류 발생 {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseResultCode.error(ErrorCode.INTERNAL_SERVER_ERROR.name()));
    }

    // 어플리케이션 Runtime 중에 발생하는 예외(db crud 등등)를 핸들링하는 함수
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<?> securityExceptionHandler() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseResultCode.error(ErrorCode.BAD_REQUEST.name()));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<?> loginCheckExceptionHandler(JsonProcessingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseResultCode.error(e.getMessage()));
    }
}
