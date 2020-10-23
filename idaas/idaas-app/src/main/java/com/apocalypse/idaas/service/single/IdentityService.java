package com.apocalypse.idaas.service.single;

import com.apocalypse.common.data.mybatis.service.BaseService;
import com.apocalypse.idaas.module.single.Identity;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
public interface IdentityService extends BaseService<Identity, Long> {

    /**
     * 填充元素的 roles 属性
     * @param identity
     */
    void fillRoles(Identity identity);
}
