package com.study.demo.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ChangePasswordRequest(
    String newPassword,
    String confirmedNewPassword
) {
}
