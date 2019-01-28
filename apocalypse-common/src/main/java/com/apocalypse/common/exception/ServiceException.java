package com.apocalypse.common.exception;


import lombok.Data;
import org.slf4j.Logger;

@Data
public class ServiceException extends RuntimeException{

    private String code;

    public ServiceException(Logger logger, String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        wrap(logger, cause);
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    private void wrap(Logger logger, Throwable e) throws ServiceException{
        if (e instanceof DaoException) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(((DaoException) e).getCode(), e.getMessage());
        }
        if (e instanceof ServiceException) {
            throw new ServiceException(((ServiceException) e).getCode(), e.getMessage());
        }
    }
}
