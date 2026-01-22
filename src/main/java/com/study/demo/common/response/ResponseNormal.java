package com.study.demo.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class ResponseNormal<T> {
    @JsonProperty("data")
    private T data;

    @JsonProperty("message")
    private String message;

    public static ResponseNormal<Void> createResponse(final String message) {
        final ResponseNormal<Void> res = new ResponseNormal<>();
        res.setMessage(message);
        return res;
    }

    public static <T> ResponseNormal<T> createResponse(final T data, final String message) {
        final ResponseNormal<T> res = new ResponseNormal<>();
        res.setData(data);
        res.setMessage(message);
        return res;
    }
}
