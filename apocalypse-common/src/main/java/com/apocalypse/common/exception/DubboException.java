package com.apocalypse.common.exception;


import lombok.Data;
import org.slf4j.Logger;

@Data
public class DubboException extends RuntimeException{

    private String code;

    public DubboException(Logger logger, String code, String message, Throwable cause, String temp, Object... args) {
        super(message, cause);
        this.code = code;
        wrap(logger, cause, temp, args);
    }

    public DubboException(String code, String message) {
        super(message);
        this.code = code;
    }

    private void wrap(Logger logger, Throwable e, String temp, Object... args) throws DubboException {
        if (e instanceof ServiceException) {
            logger.error(e.getMessage(), e);
            throw new DubboException(((ServiceException) e).getCode(), e.getMessage());
        } else if (e instanceof DubboException) {
            throw new DubboException(((DubboException) e).getCode(), e.getMessage());
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
