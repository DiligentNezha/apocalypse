package com.apocalypse.common.core.exception;


import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ServiceException extends RuntimeException {

    /**
     * 错误码标识
     */
    private String code;

    /**
     * 应用标识
     */
    private String appIdentityCode;

    /**
     * 应用上下文
     */
    private ObjectNode context;

    /**
     * 异常堆栈
     */
    private StackTraceElement[] stackTraceElement;

    /**
     * 用户提示
     */
    private String tip;

    private ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String appIdentityCode, StackTraceElement[] stackTraceElement, String message, String tip) {
        super(message);
        this.code = code;
        this.stackTraceElement = stackTraceElement;
        this.appIdentityCode = appIdentityCode;
        this.tip = tip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppIdentityCode() {
        return appIdentityCode;
    }

    public void setAppIdentityCode(String appIdentityCode) {
        this.appIdentityCode = appIdentityCode;
    }

    public ObjectNode getContext() {
        return context;
    }

    public void setContext(ObjectNode context) {
        this.context = context;
    }

    public StackTraceElement[] getStackTraceElement() {
        return stackTraceElement;
    }

    public void setStackTraceElement(StackTraceElement[] stackTraceElement) {
        this.stackTraceElement = stackTraceElement;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public ServiceException formatTip(Object... params) {
        if (params.length > 0) {
            // 按顺序将变长参数填充到提示模板中的 {} 占位符中
            tip = StrUtil.format(tip, params);
        }
        return this;
    }
}
