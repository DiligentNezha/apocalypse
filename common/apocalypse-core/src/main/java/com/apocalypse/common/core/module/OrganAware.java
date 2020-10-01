package com.apocalypse.common.core.module;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/8/25
 */
public interface OrganAware {

    /**
     * 当前身份所属的机构 ID
     * @return
     */
    Long currentOrganId();
}
