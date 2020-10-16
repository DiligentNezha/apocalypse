package com.gkjx.saas.health.system.service.single.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.gkjx.common.core.util.ServiceExceptionUtil;
import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.error.SysManageErrorCodeEnum;
import com.gkjx.saas.health.system.mapper.single.IdentityMapper;
import com.gkjx.saas.health.system.mapper.single.StaffMapper;
import com.gkjx.saas.health.system.model.single.Identity;
import com.gkjx.saas.health.system.model.single.Staff;
import com.gkjx.saas.health.system.service.single.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
@Slf4j
@Service
public class StaffServiceImpl extends BaseServiceImpl<Staff, Long> implements StaffService {

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private IdentityMapper identityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyMobile(Long staffId, String mobile) {
        if (StrUtil.isNotBlank(mobile)) {
            Staff staffByMobile = staffMapper.selectOneByProperty(Staff::getMobile, mobile);
            if (ObjectUtil.isNotNull(staffByMobile)) {
                if (!staffByMobile.getId().equals(staffId)) {
                    // 要更新的手机号是其他员工的手机号
                    throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.LOGIN_IDENTITY_USED_BY_OTHER, mobile);
                } else {
                    // 手机号未更换
                }
            } else {
                // 系统中不存在的新手机号，可以绑定
                Staff staff = new Staff();
                staff.setId(staffId);
                staff.setMobile(mobile);
                staffMapper.updateByPrimaryKeySelective(staff);

                Identity identity = identityMapper.selectOneByProperty(Identity::getStaffId, staffId);
                identity.setMobile(mobile);
                identityMapper.updateByPrimaryKeySelective(identity);
            }
        }
    }

    @Override
    public void modifyEmail(Long staffId, String email) {
        if (StrUtil.isNotBlank(email)) {
            Staff staffByEmail = staffMapper.selectOneByProperty(Staff::getEmail, email);
            if (ObjectUtil.isNotNull(staffByEmail)) {
                if (!staffByEmail.getId().equals(staffId)) {
                    // 要更新的邮箱是其他员工的邮箱
                    throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.LOGIN_IDENTITY_USED_BY_OTHER, email);
                } else {
                    // 邮箱未更换
                }
            } else {
                // 系统中不存在的新邮箱，可以绑定
                Staff staff = new Staff();
                staff.setId(staffId);
                staff.setEmail(email);
                staffMapper.updateByPrimaryKeySelective(staff);

                Identity identity = identityMapper.selectOneByProperty(Identity::getStaffId, staffId);
                identity.setEmail(email);
                identityMapper.updateByPrimaryKeySelective(identity);
            }
        }
    }
}
