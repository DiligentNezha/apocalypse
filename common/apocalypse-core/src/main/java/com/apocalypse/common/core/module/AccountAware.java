package com.apocalypse.common.core.module;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/8/25
 */
public interface AccountAware {

    /**
     * 当前身份的账户 ID
     * @return
     */
    Long currentAccountId();
}
