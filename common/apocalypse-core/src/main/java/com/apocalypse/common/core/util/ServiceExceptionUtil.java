package com.apocalypse.common.core.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.core.enums.error.ErrorCodeEnumerable;
import com.apocalypse.common.core.enums.error.SystemErrorCodeEnum;
import com.apocalypse.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@PropertySource("classpath:app.properties")
public class ServiceExceptionUtil implements InitializingBean {

    @Value("${appIdentityCode}")
    private String appIdentityCode;

    private static String APP_IDENTITY_CODE;

    /**
     * 错误码提示模板
     */
    private static final Map<String, String> msg = new ConcurrentHashMap<>();

    private static final Map<String, ErrorCodeEnumerable> errorEnum = new ConcurrentHashMap<>();

    public static void put(String code, String message) {
        ServiceExceptionUtil.msg.put(code, message);
    }

    public static void put(String code, ErrorCodeEnumerable message) {
        ServiceExceptionUtil.errorEnum.put(code, message);
    }

    public static ErrorCodeEnumerable get(String code) {
        return errorEnum.get(code);
    }

    public static ServiceException error(ErrorCodeEnumerable errorCode, Throwable e, Object... params) {
        return doFormat(errorCode, acquireStackTraceElement(e), params);
    }

    public static ServiceException fail(ErrorCodeEnumerable errorCode, Object... params) {
        return doFormat(errorCode, null, params);
    }

    public static ServiceException transform(Throwable e) {
        return doFormat(SystemErrorCodeEnum.BUSY, acquireStackTraceElement(e));
    }

    private static ServiceException doFormat(ErrorCodeEnumerable errorCode, StackTraceElement[] stackTraceElement, Object... params) {
        String code = errorCode.getCode();
        String msgTemplate = errorCode.getMsgTemplate();
        String tip = errorCode.getTip();

        Map<String, String> context = new HashMap<>();
        context.put("appIdentityCode", APP_IDENTITY_CODE);

        // 填充 appIdentityCode，将应用唯一标识填充到错误码中的 {appIdentityCode} 中
        code = StrUtil.format(code, context);

        // TODO 填充上下文,带日志系统使用后再完善

        String message = msgTemplate;

        if (params.length > 0) {
            // 按顺序将变长参数填充值消息模板中的 {} 占位符中
            message = StrUtil.format(msgTemplate, params);
        }
        return new ServiceException(code, APP_IDENTITY_CODE, stackTraceElement, message, tip);
    }

    private static StackTraceElement[] acquireStackTraceElement(Throwable e) {
        StackTraceElement[] stackTrace = null;
        if (ObjectUtil.isNotNull(e)) {
            stackTrace = e.getStackTrace();
        }
        return stackTrace;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_IDENTITY_CODE = appIdentityCode;
    }

    public static String getAppIdentityCode() {
        return APP_IDENTITY_CODE;
    }
}
