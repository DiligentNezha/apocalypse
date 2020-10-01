package com.apocalypse.common.core.enums.error;

import com.apocalypse.common.core.constant.ErrorCodeType;

/**
 * @author <a href="kaihuijing@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/7/16
 */
public interface NetworkErrorCodeEnumerable extends ErrorCodeEnumerable {

    @Override
    default String errorType() {
        return ErrorCodeType.NETWORK;
    }
}
