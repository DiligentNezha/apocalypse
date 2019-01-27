package com.apocalypse.common.exception;


import lombok.Data;

@Data
public class FrontException extends Exception{

    private String code;

    private String msg;

    public FrontException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public FrontException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public FrontException() {
        super();
    }

    public FrontException(String message) {
        super(message);
    }

    public FrontException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrontException(Throwable cause) {
        super(cause);
    }
}
