package com.study.user.controller.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordRequest {
    private String newPassword;
    private String confirmedNewPassword;
}
