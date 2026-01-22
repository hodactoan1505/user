package com.study.demo.handler;

import com.study.demo.common.exception.BusinessException;
import com.study.demo.common.response.ResponseError;
import com.study.demo.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = BusinessException.class)
    public ResponseEntity<ResponseError> businessHandler(final BusinessException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(
            ResponseError.builder()
                .code(e.getErrorCode().getCode())
                .message(e.getErrorCode().getMessage())
                .build());
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ResponseError> exceptionHandler(final Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body(
            ResponseError.builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build());
    }
}
