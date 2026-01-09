package com.study.user.controller.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AuthResponse(
    @JsonProperty("accessToken")
    String accessToken
) {
}
