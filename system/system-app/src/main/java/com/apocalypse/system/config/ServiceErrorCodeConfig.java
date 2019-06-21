package com.apocalypse.system.config;

import com.apocalypse.common.util.ServiceExceptionUtil;
import com.apocalypse.system.enums.SystemErrorCodeEnum;
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
        for (SystemErrorCodeEnum systemErrorCodeEnum : SystemErrorCodeEnum.values()) {
            ServiceExceptionUtil.put(systemErrorCodeEnum.getCode(), systemErrorCodeEnum.getMessage());
        }
    }
}
