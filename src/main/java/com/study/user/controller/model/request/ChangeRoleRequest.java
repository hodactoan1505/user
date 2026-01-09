package com.study.user.controller.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChangeRoleRequest(
    List<String> roleList
) {
}
