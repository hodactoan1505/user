package com.study.demo.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // --- General & System Errors (GEN) ---
    UNCATEGORIZED_EXCEPTION("GEN001", "Uncategorized error occurred"),
    INVALID_KEY("GEN002", "Invalid message key"),
    INVALID_INPUT("GEN003", "Invalid input data"),

    // --- Authentication & Authorization (AUTH) ---
    UNAUTHENTICATED("AUTH001", "Invalid username or password"),
    UNAUTHORIZED("AUTH002", "You do not have permission to access this resource"),
    TOKEN_EXPIRED("AUTH003", "Session has expired, please login again"),

    // --- User Management (USER) ---
    USER_EXISTED("USER001", "User already exists"),
    USER_NOT_FOUND("USER002", "User could not be found"),
    INVALID_PASSWORD_FORMAT("USER003", "Password does not meet complexity requirements"),

    // --- Common ----
    DATA_EXISTED("CO001", "User already exists"),
    DATA_NOT_FOUND("CO002", "User could not be found");

    private final String code;
    private final String message;

    ErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
