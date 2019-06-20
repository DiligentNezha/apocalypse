package com.apocalypse.common.exception;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.enums.SysErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler
    public Rest handler(Throwable e) {
        //处理异常
        log.error(e.getMessage(), e);
        Rest<Object> response = new Rest<>();
        response.setSuccess(false);
        return Rest.error(SysErrorCodeEnum.SYS_ERROR);
    }

    @ExceptionHandler
    public Rest handler(ServiceException e) {
        //处理异常
        log.warn(e.getMessage());
        return Rest.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler
    public Rest handler(MethodArgumentNotValidException e) {
        //处理异常
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        JSONArray jsonArray = new JSONArray();
        String msg = "参数校验异常！";
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                String field = ((FieldError) error).getField();
                String defaultMessage = error.getDefaultMessage();
                Object rejectedValue = ((FieldError) error).getRejectedValue();
                jsonArray.fluentAdd(new JSONObject()
                        .fluentPut("参数", field)
                        .fluentPut("接收到的值", ObjectUtil.defaultIfNull(rejectedValue, "null"))
                        .fluentPut("错误信息", defaultMessage));
                msg = jsonArray.toJSONString();
            } else if (error instanceof ObjectError) {
                msg = error.getDefaultMessage();
            }
        }
        log.warn("参数校验异常！{}", msg);
        return Rest.error(SysErrorCodeEnum.VALIDATION_REQUEST_PARAM_ERROR, msg);
    }

}
