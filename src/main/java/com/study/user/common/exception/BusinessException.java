package com.study.user.common.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
