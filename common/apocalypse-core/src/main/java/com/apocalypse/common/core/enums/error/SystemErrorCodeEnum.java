package com.apocalypse.common.core.enums.error;

import cn.hutool.core.collection.CollUtil;
import com.apocalypse.common.core.constant.Modules;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用系统异常
 * <p>
 * 参考 阿里Java 开发手册（泰山版）
 * <p>
 * 第一部分: 一位错误产生来源标识
 * U - 表示错误来源于用户，如参数错误等
 * S - 表示错误来源于当前系统，如业务逻辑出错等
 * T - 表示错误来源于三方服务
 * <p>
 * 第二部分: 四位数字编码
 * 说明:
 * 1.根据类型步长间距可预留100
 * 2.错误码不与HTTP状态码关联
 * 3.者避免随意定义新的错误码, 尽可能在原有错误码附表中找到语义相同或者相近的错误码在代码中使用即可
 *
 * <p>
 */
public enum SystemErrorCodeEnum implements SystemErrorCodeEnumerable {

    SUCCESS("SUCCESS", "操作成功", "操作成功"),

    BUSY("BUSY", "系统繁忙", "系统繁忙"),

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

    SystemErrorCodeEnum(String code, String msgTemplate, String tip) {
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
    public SystemErrorCodeEnum acquire(String code) {
        List<SystemErrorCodeEnum> collect = Arrays.stream(values()).filter(i -> i.getCode().equals(code)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }
}
