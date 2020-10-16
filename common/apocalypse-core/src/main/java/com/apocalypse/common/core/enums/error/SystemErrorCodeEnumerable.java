package com.apocalypse.common.core.enums.error;

import com.apocalypse.common.core.constant.ErrorCodeType;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/7/16
 */
public interface SystemErrorCodeEnumerable extends ErrorCodeEnumerable {

    @Override
    default String errorType() {
        return ErrorCodeType.SYSTEM;
    }
}
