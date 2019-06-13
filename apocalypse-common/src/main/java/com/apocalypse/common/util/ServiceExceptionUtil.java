package com.apocalypse.common.util;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.enums.ErrorCodeMark;
import com.apocalypse.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link ServiceException} 工具类
 *
 * 目的在于，格式化异常信息提示。
 * 考虑到 String.format 在参数不正确时会报错，因此使用 {} 作为占位符，并使用 {@link #doFormat(int, String, Object...)} 方法来格式化
 *
 * 因为 {@link #msg} 里面默认是没有异常信息提示的模板的，所以需要使用方自己初始化进去。目前想到的有几种方式：
 *
 * 1. 异常提示信息，写在枚举类中，例如说，cn.iocoder.oceans.user.api.constants.ErrorCodeEnum 类 + ServiceExceptionConfiguration
 * 2. 异常提示信息，写在 .properties 等等配置文件
 * 3. 异常提示信息，写在 Apollo 等等配置中心中，从而实现可动态刷新
 * 4. 异常提示信息，存储在 db 等等数据库中，从而实现可动态刷新
 */
@Slf4j
public class ServiceExceptionUtil {

    /**
     * 错误码提示模板
     */
    private static final ConcurrentMap<Integer, String> msg = new ConcurrentHashMap<>();

    public static void putAll(Map<Integer, String> msg) {
        ServiceExceptionUtil.msg.putAll(msg);
    }

    public static void put(Integer code, String message) {
        ServiceExceptionUtil.msg.put(code, message);
    }

    public static <T> Rest<T> error(ErrorCodeMark errorCodeMark) {
        Integer code = errorCodeMark.getCode();
        return Rest.error(errorCodeMark, msg.get(code));
    }

    public static Rest error(ErrorCodeMark errorCodeMark, Object... params) {
        Integer code = errorCodeMark.getCode();
        String message = doFormat(code, msg.get(code), params);
        return Rest.error(errorCodeMark, message);
    }

    /**
     * 创建指定编号的 ServiceException 的异常
     *
     * @param errorCodeMark 编号
     * @return 异常
     */
    public static ServiceException exception(ErrorCodeMark errorCodeMark) {
        Integer code = errorCodeMark.getCode();
        return new ServiceException(code, msg.get(code));
    }

    /**
     * 创建指定编号的 ServiceException 的异常
     *
     * @param errorCodeMark 编号
     * @param params 消息提示的占位符对应的参数
     * @return 异常
     */
    public static ServiceException exception(ErrorCodeMark errorCodeMark, Object... params) {
        Integer code = errorCodeMark.getCode();
        String message = doFormat(code, msg.get(code), params);
        return new ServiceException(code, message);
    }

    public static ServiceException exception(ErrorCodeMark errorCodeMark, String messagePattern, Object... params) {
        Integer code = errorCodeMark.getCode();
        String message = doFormat(code, messagePattern, params);
        return new ServiceException(code, message);
    }

    /**
     * 将错误编号对应的消息使用 params 进行格式化。
     *
     * @param code           错误编号
     * @param messagePattern 消息模版
     * @param params         参数
     * @return 格式化后的提示
     */
    private static String doFormat(Integer code, String messagePattern, Object... params) {
        StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
        int i = 0;
        int j;
        int l;
        for (l = 0; l < params.length; l++) {
            j = messagePattern.indexOf("{}", i);
            if (j == -1) {
                log.error("[doFormat][参数过多：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
                if (i == 0) {
                    return messagePattern;
                } else {
                    sbuf.append(messagePattern.substring(i, messagePattern.length()));
                    return sbuf.toString();
                }
            } else {
                sbuf.append(messagePattern.substring(i, j));
                sbuf.append(params[l]);
                i = j + 2;
            }
        }
        if (messagePattern.indexOf("{}", i) != -1) {
            log.error("[doFormat][参数过少：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
        }
        sbuf.append(messagePattern.substring(i, messagePattern.length()));
        return sbuf.toString();
    }

}
