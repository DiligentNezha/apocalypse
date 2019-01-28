package com.apocalypse.common.exception;


import lombok.Data;

@Data
public class DaoException extends RuntimeException{

    private String code;

    public DaoException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
