package com.apocalypse.front.exception;


public class FrontException extends Exception{
    private String code;
    private String msg;
    private static final long serialVersionUID = -3880057315966629842L;

    public FrontException() {
    }

    public FrontException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public FrontException(String msg) {
        super(msg);
    }

    public FrontException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public FrontException(Throwable cause) {
        super(cause);
    }
}
