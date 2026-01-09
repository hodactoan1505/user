package com.study.user.controller.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.user.controller.model.response.part.RolePart;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDetailResponse(
    @JsonProperty("id")
    Long id,

    @JsonProperty("username")
    String username,

    @JsonProperty("isActive")
    boolean active,

    @JsonProperty("roleDtoList")
    List<RolePart> rolePartList
) {
}
