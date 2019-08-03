package com.apocalypse.common.enums;

/**
 * 服务异常，通用系统异常
 *
 * 参考 https://www.kancloud.cn/onebase/ob/484204 文章
 *
 * 一共 10 位，分成四段
 *
 * 第一段，1 位，类型
 *      1 - 系统级别异常
 *      2 - 业务级别异常
 * 第二段，3 位，系统类型
 *      001 - 演示系统
 *      002 - 管理系统
 *
 * 第三段，3 位，模块
 *      不限制规则。
 *      一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 *          001 - OAuth2 模块
 *          002 - User 模块
 *          003 - MobileCode 模块
 * 第四段，3 位，错误码
 *       不限制规则。
 *       一般建议，每个模块自增。
 */
public enum SysErrorCodeEnum implements ErrorCodeMark{

    SYSTEM_BUSY(1001001000, "系统繁忙"),
    MISSING_REQUEST_PARAMETER(1001001001, "参数缺失"),
    REQUEST_PARAMETER_NOT_VALID(1001001002, "参数校验不正确"),
    METHOD_NOT_SUPPORT(1001001003, "请求方法不支持"),
    ;

    private final Integer code;
    private final String message;

    SysErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }

    public String getMessage() {
        return message;
    }
}
