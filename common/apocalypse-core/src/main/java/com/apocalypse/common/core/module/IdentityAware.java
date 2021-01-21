package com.apocalypse.common.core.module;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/8/25
 */
public interface IdentityAware {

    /**
     * 当前身份对应的员工 ID
     * @return
     */
    Long currentStaffId();

    /**
     * 当前身份 ID
     * @return
     */
    Long currentIdentityId();
}
