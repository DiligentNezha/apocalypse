package com.apocalypse.idaas.config;

import com.apocalypse.common.core.enums.error.SystemErrorCodeEnum;
import com.apocalypse.common.core.util.ServiceExceptionUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/13
 */
@Configuration
public class ServiceErrorCodeConfig {

    @EventListener(ApplicationReadyEvent.class)
    public void registerSystemErrorCode() {
        String appIdentityCode = ServiceExceptionUtil.getAppIdentityCode();
        for (SystemErrorCodeEnum errorCodeEnum : SystemErrorCodeEnum.values()) {
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum.getMsgTemplate());
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum);
        }
    }
}
