package com.apocalypse.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(description = "响应类")
public class Rest<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean success = Boolean.TRUE;

    private Integer code;

    private String msg;

    private T data;

    public static Rest ok() {
        return new Rest();
    }

    public static <T> Rest<T> ok(T data) {
        Rest<T> rest = new Rest<>();
        rest.setData(data);
        return rest;
    }

    public static Rest error() {
        return new Rest<>()
                .setSuccess(false);
    }

}
