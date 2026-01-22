package com.study.demo.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChangeRoleRequest(
    List<String> roleList
) {
}
