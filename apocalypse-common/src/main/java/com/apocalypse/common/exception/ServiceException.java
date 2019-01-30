package com.apocalypse.common.exception;


import lombok.Data;
import org.slf4j.Logger;

@Data
public class ServiceException extends RuntimeException{

    private String code;

    public ServiceException(Logger logger, String code, String message, Throwable cause, String temp, Object... args) {
        super(message, cause);
        this.code = code;
        wrap(logger, cause, temp, args);
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    private void wrap(Logger logger, Throwable e, String temp, Object... args) throws ServiceException{
        if (e instanceof DaoException) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(((DaoException) e).getCode(), e.getMessage());
        } else if (e instanceof ServiceException) {
            throw new ServiceException(((ServiceException) e).getCode(), e.getMessage());
        } else {
            if (args != null && args.length > 0) {
                logger.error(temp, args);
                logger.error("detail", e);
            }else {
                logger.error(temp, e);
            }
        }
    }
}
