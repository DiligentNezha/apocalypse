package com.apocalypse.common.exception;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.enums.SysErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.util.Iterator;
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
        return Rest.error(SysErrorCodeEnum.SYSTEM_BUSY);
    }

    @ExceptionHandler
    public Rest handler(ServiceException e) {
        //处理异常
        log.warn(e.getMessage());
        return Rest.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler
    public Rest handler(ConstraintViolationException e) {
        //处理异常
        JSONArray jsonArray = new JSONArray();
        e.getConstraintViolations().stream().forEach(constraintViolation -> {
            Iterator<Path.Node> iterator = constraintViolation.getPropertyPath().iterator();
            while (iterator.hasNext()) {
                Path.Node next = iterator.next();
                if (ElementKind.PARAMETER.equals(next.getKind())) {
                    jsonArray.fluentAdd(new JSONObject()
                            .fluentPut("参数", next.getName())
                            .fluentPut("接收到的值", ObjectUtil.defaultIfNull(constraintViolation.getInvalidValue(), "null"))
                            .fluentPut("错误信息", constraintViolation.getMessage()));
                }
            }
        });

        return Rest.error(SysErrorCodeEnum.REQUEST_PARAMETER_NOT_VALID, jsonArray.size() > 0 ?
                jsonArray.toJSONString() : e.getMessage());
    }

    @ExceptionHandler
    public Rest handler(MethodArgumentNotValidException e) {
        //处理异常
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        JSONArray jsonArray = new JSONArray();
        String msg = SysErrorCodeEnum.REQUEST_PARAMETER_NOT_VALID.getMsg();
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
            } else {
                msg = error.getDefaultMessage();
            }
        }
        log.warn("参数校验异常[{}]", msg);
        return Rest.error(SysErrorCodeEnum.REQUEST_PARAMETER_NOT_VALID, msg);
    }

    @ExceptionHandler
    public Rest handler(MissingServletRequestParameterException e) {
        //处理异常
        Rest<Object> response = new Rest<>();
        response.setSuccess(false)
                .setCode(SysErrorCodeEnum.MISSING_REQUEST_PARAMETER.getCode());
        String parameterName = e.getParameterName();
        String message = e.getMessage();
        JSONObject msg = new JSONObject().fluentPut(parameterName, message);
        log.warn("参数校验异常[{}]", msg);
        response.setMsg(msg.toJSONString());
        return response;
    }

    @ExceptionHandler
    public Rest handler(HttpRequestMethodNotSupportedException e) {
        //处理异常
        log.warn(e.getMessage(), e);
        Rest<Object> response = new Rest<>();
        response.setSuccess(false)
                .setCode(SysErrorCodeEnum.METHOD_NOT_SUPPORT.getCode())
                .setMsg(e.getMessage());
        return response;
    }
}
