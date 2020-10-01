package com.apocalypse.common.core.enums.error;

/**
 * @author <a href="kaihuijing@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/7/16
 */
public interface BusinessErrorCodeEnumerable extends ErrorCodeEnumerable {

    @Override
    default String errorType() {
        return "B";
    }
}
