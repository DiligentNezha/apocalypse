package com.apocalypse.common.exception;


import lombok.Data;
import org.slf4j.Logger;

@Data
public class BusinessException extends RuntimeException{

    private String code;

    public BusinessException(Logger logger, String code, String message, Throwable cause, String temp, Object... args) {
        super(message, cause);
        this.code = code;
        wrap(logger, cause, temp, args);
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    private void wrap(Logger logger, Throwable e, String temp, Object... args) throws BusinessException {
        if (e instanceof DubboException) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(((DubboException) e).getCode(), e.getMessage());
        } else if (e instanceof BusinessException) {
            throw new BusinessException(((BusinessException) e).getCode(), e.getMessage());
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
