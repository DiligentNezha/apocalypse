package com.apocalypse.common.dto;

import com.apocalypse.common.enums.ErrorCodeMark;
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

    private Integer code = 0;

    private String msg;

    private T data;

    private Page page;

    public static Rest ok() {
        return new Rest();
    }

    public static <T> Rest<T> ok(T data) {
        Rest<T> rest = new Rest<>();
        rest.setData(data);
        return rest;
    }

    public static Rest error(ErrorCodeMark errorCodeMark, String msg) {
        return new Rest<>()
                .setCode(errorCodeMark.getCode())
                .setMsg(msg)
                .setSuccess(false);
    }

    public static Rest error(ErrorCodeMark errorCodeMark) {
        return new Rest<>()
                .setCode(errorCodeMark.getCode())
                .setMsg(errorCodeMark.getMsg())
                .setSuccess(false);
    }

    public static Rest error(Integer code, String msg) {
        return new Rest<>()
                .setCode(code)
                .setMsg(msg)
                .setSuccess(false);
    }

}
