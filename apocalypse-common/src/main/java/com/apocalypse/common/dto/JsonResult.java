package com.apocalypse.common.dto;

import java.io.Serializable;

public class JsonResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private Object data;

    public JsonResult(){

    }

    public JsonResult(String msg) {
        this.msg = msg;
    }

    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
