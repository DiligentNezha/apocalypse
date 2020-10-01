package com.apocalypse.common.core.enums.error;

import cn.hutool.core.collection.CollUtil;
import com.apocalypse.common.core.constant.Modules;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ParameterErrorCodeEnum implements SystemErrorCodeEnumerable {

    /**
     * 缺少参数
     */
    MISSING_REQUEST_PARAMETER("MISSING_REQUEST_PARAMETER", "缺少参数:[{}]，类型为[{}]", "缺少参数"),

    /**
     * 非法参数
     */
    PARAMETER_NOT_VALID("REQUEST_PARAMETER_NOT_VALID", "参数校验不正确:[{}]", "参数不正确"),

    /**
     * 请求方法不支持
     */
    METHOD_NOT_SUPPORT("METHOD_NOT_SUPPORT", "请求方法[{}]不支持，支持的方法为{}", "请求方法不支持"),

    /**
     * 接口不存在
     */
    NO_HANDLER_FOUND("NO_HANDLER_FOUND", "接口[{}]不存在", "接口不存在"),
    ;

    /**
     * 错误码标识
     */
    private String code;

    /**
     * 错误消息模板
     */
    private String msgTemplate;

    /**
     * 用户提示
     */
    private String tip;

    ParameterErrorCodeEnum(String code, String msgTemplate, String tip) {
        this.code = new StringBuilder(errorType())
                .append(".").append("{appIdentityCode}")
                .append(".").append(getModuleName())
                .append(".").append(code)
                .toString();
        this.msgTemplate = msgTemplate;
        this.tip = tip;

    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getModuleName() {
        return Modules.AUTHORIZATION;
    }

    @Override
    public String getMsgTemplate() {
        return msgTemplate;
    }

    @Override
    public String getTip() {
        return tip;
    }

    /**
     * 根据 code 查找对应的枚举对象
     * @param code
     */
    public ParameterErrorCodeEnum acquire(String code) {
        List<ParameterErrorCodeEnum> collect = Arrays.stream(values()).filter(i -> i.getCode().equals(code)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }
}
