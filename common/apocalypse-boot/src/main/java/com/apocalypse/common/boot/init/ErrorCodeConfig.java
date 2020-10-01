package com.apocalypse.common.boot.init;

import com.apocalypse.common.core.enums.error.*;
import com.apocalypse.common.core.util.ServiceExceptionUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class ErrorCodeConfig {

    @EventListener(ApplicationReadyEvent.class)
    public void registerSystemErrorCode() {
        String appIdentityCode = ServiceExceptionUtil.getAppIdentityCode();

        for (AuthenticationErrorCodeEnum errorCodeEnum : AuthenticationErrorCodeEnum.values()) {
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum.getMsgTemplate());
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum);
        }

        for (AuthorizationErrorCodeEnum errorCodeEnum : AuthorizationErrorCodeEnum.values()) {
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum.getMsgTemplate());
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum);
        }

        for (BusinessErrorCodeEnum errorCodeEnum : BusinessErrorCodeEnum.values()) {
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum.getMsgTemplate());
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum);
        }

        for (ParameterErrorCodeEnum errorCodeEnum : ParameterErrorCodeEnum.values()) {
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum.getMsgTemplate());
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum);
        }

        for (SystemErrorCodeEnum errorCodeEnum : SystemErrorCodeEnum.values()) {
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum.getMsgTemplate());
            ServiceExceptionUtil.put(errorCodeEnum.fillAppIdentityCode(appIdentityCode), errorCodeEnum);
        }
    }
}