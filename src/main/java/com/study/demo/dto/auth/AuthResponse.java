package com.study.demo.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AuthResponse(
    @JsonProperty("accessToken")
    String accessToken
) {
}
