package com.apocalypse.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "响应类")
public class Rest<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

}
