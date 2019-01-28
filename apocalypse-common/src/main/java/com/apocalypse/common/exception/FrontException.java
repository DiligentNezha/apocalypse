package com.apocalypse.common.exception;


import lombok.Data;

@Data
public class FrontException extends RuntimeException{

    private String code;

    public FrontException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public FrontException(String code, String message) {
        super(message);
        this.code = code;
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
