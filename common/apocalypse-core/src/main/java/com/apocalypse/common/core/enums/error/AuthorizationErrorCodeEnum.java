package com.apocalypse.common.core.enums.error;

import cn.hutool.core.collection.CollUtil;
import com.apocalypse.common.core.constant.Modules;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 授权失败错误码
 */
public enum AuthorizationErrorCodeEnum implements SystemErrorCodeEnumerable {

    /**
     * 访问拒绝
     */
    ACCESS_DENIED("ACCESS_DENIED", "访问拒绝", "您没有访问权限"),

    /**
     * 缺少 CSRF 令牌
     */
    MISSING_CSRF_TOKEN("MISSING_CSRF_TOKEN", "缺少 CSRF 令牌", "非法的跨站点请求"),

    /**
     * 非法的跨站点请求
     */
    INVALID_CSRF_TOKEN("INVALID_CSRF_TOKEN", "非法的跨站点请求", "非法的跨站点请求"),

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

    AuthorizationErrorCodeEnum(String code, String msgTemplate, String tip) {
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
    public AuthorizationErrorCodeEnum acquire(String code) {
        List<AuthorizationErrorCodeEnum> collect = Arrays.stream(values()).filter(i -> i.getCode().equals(code)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }


}
