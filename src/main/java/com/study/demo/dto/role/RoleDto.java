package com.study.demo.dto.role;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoleDto(
    @JsonProperty("code")
    String code,

    @JsonProperty("name")
    String name
) {

}
