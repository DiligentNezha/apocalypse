package com.apocalypse.system.enums;

import com.apocalypse.common.enums.ErrorCodeMark;

/**
 * 错误码枚举类
 *
 * 系统，使用 1-002-000-000 段
 */
public enum SystemErrorCodeEnum implements ErrorCodeMark {

    // ========== OAUTH2 模块 ==========
    OAUTH2_UNKNOWN(2002001000, "未知错误"), // 预留
//    OAUTH2_INVALID_GRANT_BAD_CREDENTIALS(2001001001, "密码不正确"), // 暂时没用到
//    OAUTH2_INVALID_GRANT_USERNAME_NOT_FOUND(2001001002, "账号不存在"), // 暂时没用到
//    OAUTH2_INVALID_GRANT(2001001010, ""), // 预留
    OAUTH2_INVALID_TOKEN_NOT_FOUND(2002001011, "访问令牌不存在"),
    OAUTH2_INVALID_TOKEN_EXPIRED(2002001012, "访问令牌已过期"),
    OAUTH2_INVALID_TOKEN_INVALID(2002001013, "访问令牌已失效"),
    OAUTH2_NOT_LOGIN(2002001015, "账号未登陆"),
    OAUTH2_INVALID_TOKEN_ERROR_USER_TYPE(2002001016, "访问令牌用户类型不正确"),
    OAUTH_INVALID_REFRESH_TOKEN_NOT_FOUND(2002001017, "刷新令牌不存在"),
    OAUTH_INVALID_REFRESH_TOKEN_EXPIRED(2002001018, "访问令牌已过期"),
    OAUTH_INVALID_REFRESH_TOKEN_INVALID(2002001019, "刷新令牌已失效"),

    // ========== 管理员模块 1002002000 ==========
    ADMIN_USERNAME_NOT_REGISTERED(2002002000, "账号不存在"),
    ADMIN_PASSWORD_ERROR(2002002001, "密码不正确"),
    ADMIN_IS_DISABLE(2002002002, "账号被禁用"),
    ADMIN_USERNAME_EXISTS(2002002002, "账号已经存在"),
    ADMIN_STATUS_EQUALS(2002002003, "账号已经是该状态"),
    ADMIN_DELETE_ONLY_DISABLE(2002002004, "只有关闭的账号才可以删除"),
    ADMIN_ADMIN_STATUS_CAN_NOT_UPDATE(2002002005, "管理员的账号状态不允许变更"),
    ADMIN_ASSIGN_ROLE_NOT_EXISTS(2002002006, "分配员工角色时，有角色不存在"),
    ADMIN_INVALID_PERMISSION(2002002007, "没有该操作权限"),
    ADMIN_ADMIN_CAN_NOT_UPDATE(2002002008, "管理员的账号不允许变更"),
    ADMIN_DEMO_CAN_NOT_WRITE(2002002009, "演示账号，暂不允许写操作。欢迎加入我们的交流群：http://t.cn/EKEr5WE"),

    // ========== 资源模块 1002003000 ==========
    RESOURCE_NAME_DUPLICATE(2002003000, "已经存在该名字的资源"),
    RESOURCE_PARENT_NOT_EXISTS(2002003001, "父资源不存在"),
    RESOURCE_PARENT_ERROR(2002003002, "不能设置自己为父资源"),
    RESOURCE_NOT_EXISTS(2002003003, "资源不存在"),
    RESOURCE_EXISTS_CHILDREN(2002003004, "存在子资源，无法删除"),
    RESOURCE_PARENT_NOT_MENU(2002003005, "父资源的类型必须是菜单"),

    // ========== 角色模块 1002004000 ==========
    ROLE_NOT_EXISTS(2002004000, "角色不存在"),
    ROLE_ASSIGN_RESOURCE_NOT_EXISTS(2002004001, "分配角色资源时，有资源不存在"),

    // ========== 数据字典模块 1002005000 ==========
    DATA_DICT_EXISTS(2002005000, "该数据字典已经存在"),
    DATA_DICT_NOT_EXISTS(2002005001, "该数据字典不存在"),

    // ========== 短信模板 1002006000 ==========
    SMS_PLATFORM_FAIL(2002006000, "短信平台调用失败【具体错误会动态替换】"),
    SMS_SIGN_NOT_EXISTENT(2002006001, "短信签名不存在"),
    SMS_SIGN_IS_EXISTENT(2002006002, "短信签名已存在"),
    SMS_TEMPLATE_NOT_EXISTENT(2002006020, "短信签名不存在"),
    SMS_TEMPLATE_IS_EXISTENT(2002006021, "短信签名不存在"),
    SMS_NOT_SEND_CLIENT(2002006030, "短信没有发送的client"),
    ;

    private final Integer code;
    private final String message;

    SystemErrorCodeEnum(int code, String message) {
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
