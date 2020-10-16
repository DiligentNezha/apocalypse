package com.gkjx.saas.health.system.service.single;


import com.gkjx.common.data.mybatis.service.BaseService;
import com.gkjx.saas.health.system.model.single.Staff;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
public interface StaffService extends BaseService<Staff, Long> {

    /**
     * 修改员工手机号
     * @param staffId
     * @param mobile
     */
    void modifyMobile(Long staffId, String mobile);

    /**
     * 修改员工邮箱
     * @param staffId
     * @param email
     */
    void modifyEmail(Long staffId, String email);
}
