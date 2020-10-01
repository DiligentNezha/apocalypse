package com.apocalypse.common.core.enums.error;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description 错误码标记接口
 * @date 2020/3/30
 */
public interface ErrorCodeEnumerable<V extends Enum> {

    /**
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
     */
    String getCode();

    void setCode(String code);

    /**
     * 获取消息模板
     * @return
     */
    String getMsgTemplate();

    /**
     * 错误类型
     * @return
     */
    String errorType();

    /**
     * 使用应用唯一标识填充 code
     * @param appIdentityCode
     * @return
     */
    default String fillAppIdentityCode(String appIdentityCode) {
        Map<String, String> context = new HashMap<>();
        context.put("appIdentityCode", appIdentityCode);
        String code = StrUtil.format(getCode(), context);
        setCode(code);
        return code;
    };

    /**
     * 提示信息
     * @return
     */
    default String getTip() {
        return "操作成功";
    }
}
