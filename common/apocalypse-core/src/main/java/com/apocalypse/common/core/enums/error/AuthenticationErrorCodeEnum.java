package com.apocalypse.common.core.enums.error;

import cn.hutool.core.collection.CollUtil;
import com.apocalypse.common.core.constant.Modules;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证失败错误码
 */
public enum AuthenticationErrorCodeEnum implements SystemErrorCodeEnumerable {

    /**
     * 认证失败
     */
    FAILURE("FAILURE", "认证失败[{}]", "认证失败{}"),

    /**
     * 不支持表单登录
     */
    FORM_LOGIN_NOT_SUPPORT("FORM_LOGIN_NOT_SUPPORT", "不支持表单登录，支持的登录形式为[{}]", "不支持表单登录"),

    /**
     * 用户名不存在
     */
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND", "用户名不存在", "用户名不存在"),

    /**
     * 认证凭证不足
     */
    INSUFFICIENT("INSUFFICIENT", "缺少认证凭证", "缺少认证凭证"),

    /**
     * 错误的访问凭证
     */
    BAD_CREDENTIALS("BAD_CREDENTIALS", "错误的访问凭证", "非法的访问凭证"),

    /**
     * 账号已锁定
     */
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "账号已锁定", "账号已锁定"),

    /**
     * 账号不可用
     */
    ACCOUNT_DISABLED("ACCOUNT_DISABLED", "账号不可用", "账号不可用"),

    /**
     * 账号已过期
     */
    ACCOUNT_EXPIRED("ACCOUNT_EXPIRED", "账号已过期", "账号已过期"),

    /**
     * 凭证已过期
     */
    CREDENTIALS_EXPIRED("CREDENTIALS_EXPIRED", "凭证已过期", "凭证已过期"),

    /**
     * 账号已在另一台设备登录
     */
    LOGIN_ON_ANOTHER_DEVICE("LOGIN_ON_ANOTHER_DEVICE", "账号已在另一台设备登录，{}", "账号已在另一台设备登录"),

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

    AuthenticationErrorCodeEnum(String code, String msgTemplate, String tip) {
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
        return Modules.AUTHENTICATION;
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
    public AuthenticationErrorCodeEnum acquire(String code) {
        List<AuthenticationErrorCodeEnum> collect = Arrays.stream(values()).filter(i -> i.getCode().equals(code)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }


}
