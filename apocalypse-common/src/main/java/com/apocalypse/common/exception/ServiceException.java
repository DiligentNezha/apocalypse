package com.apocalypse.common.exception;


import lombok.Data;
import org.slf4j.Logger;

@Data
public class ServiceException extends RuntimeException{

    private String code;

    public ServiceException(Logger logger, String code, String message, Throwable cause, String temp, Object... args) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

}
