package com.study.user.handler;

import com.study.user.common.exception.BusinessException;
import com.study.user.common.reponse.ResponseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = BusinessException.class)
    public ResponseEntity<ResponseError> businessHandler(final BusinessException e) {
        return ResponseEntity.badRequest().body(
            ResponseError.builder()
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ResponseError> exceptionHandler(final Exception e) {
        return ResponseEntity.internalServerError().body(
            ResponseError.builder()
                // TODO: Add enum
                .message("System error")
                .build());
    }
}
