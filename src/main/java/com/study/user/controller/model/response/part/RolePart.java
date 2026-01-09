package com.study.user.controller.model.response.part;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RolePart(
    @JsonProperty("code")
    String code,

    @JsonProperty("name")
    String name
) {

}
