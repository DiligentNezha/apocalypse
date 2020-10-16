package com.gkjx.saas.health.system.service.single;

import com.gkjx.common.data.mybatis.service.BaseService;
import com.gkjx.saas.health.system.model.single.Identity;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
public interface IdentityService extends BaseService<Identity, Long> {

    /**
     * 填充元素的 roles 属性
     * @param identity
     */
    void fillRoles(Identity identity);

    /**
     * 修改身份密码
     * @param staffId
     * @param originalPassword
     * @param password
     */
    void modifyPassword(Long staffId, String originalPassword, String password);

    /**
     * 重置身份密码
     * @param staffId
     * @param password
     */
    void resetPassword(Long staffId, String password);
}
