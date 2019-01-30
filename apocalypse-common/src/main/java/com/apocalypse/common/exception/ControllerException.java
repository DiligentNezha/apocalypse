package com.apocalypse.common.exception;


import lombok.Data;
import org.slf4j.Logger;

@Data
public class ControllerException extends RuntimeException{

    private String code;

    public ControllerException(Logger logger, String code, String message, Throwable cause, String temp, Object... args) {
        super(message, cause);
        this.code = code;
        wrap(logger, cause, temp, args);
    }

    public ControllerException(String code, String message) {
        super(message);
        this.code = code;
    }

    private void wrap(Logger logger, Throwable e, String temp, Object... args) throws ControllerException {
        if (e instanceof BusinessException) {
            logger.error(e.getMessage(), e);
            throw new ControllerException(((BusinessException) e).getCode(), e.getMessage());
        } else if (e instanceof ControllerException) {
            throw new ControllerException(((ControllerException) e).getCode(), e.getMessage());
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
