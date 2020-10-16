package com.gkjx.saas.health.system.service.single.impl;

import cn.hutool.core.util.ObjectUtil;
import com.gkjx.common.core.enums.error.BusinessErrorCodeEnum;
import com.gkjx.common.core.util.ServiceExceptionUtil;
import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.error.SysManageErrorCodeEnum;
import com.gkjx.saas.health.system.mapper.single.IdentityMapper;
import com.gkjx.saas.health.system.mapper.single.RoleMapper;
import com.gkjx.saas.health.system.mapper.single.AccountRoleUnionMapper;
import com.gkjx.saas.health.system.model.single.Identity;
import com.gkjx.saas.health.system.service.single.IdentityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class IdentityServiceImpl extends BaseServiceImpl<Identity, Long> implements IdentityService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private AccountRoleUnionMapper accountRoleUnionMapper;

    @Override
    public void fillRoles(Identity identity) {
//        List<AccountRoleUnion> userRoles = accountRoleUnionMapper.selectByProperty(AccountRoleUnion::getStaffId, identity.getId());
//        if (CollUtil.isEmpty(userRoles)) {
//            return;
//        }
//        List<Role> roles = roleMapper.selectByIdList(userRoles.stream().mapToLong(AccountRoleUnion::getRoleId).boxed().collect(Collectors.toList()));
//        identity.setRoles(roles);
    }

    @Resource
    private IdentityMapper identityMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void modifyPassword(Long staffId, String originalPassword, String password) {
        Identity identity = identityMapper.selectOneByProperty(Identity::getStaffId, staffId);
        if (ObjectUtil.isNull(identity)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, staffId);
        }
        if (!passwordEncoder.matches(originalPassword, identity.getPassword())) {
            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.ORIGINAL_PASSWORD_ERROR);
        }
        identity.setPassword(passwordEncoder.encode(password));
        identityMapper.updateByPrimaryKeySelective(identity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long staffId, String password) {
        Identity identity = identityMapper.selectOneByProperty(Identity::getStaffId, staffId);
        if (ObjectUtil.isNull(identity)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, staffId);
        }
        identity.setPassword(passwordEncoder.encode(password));
        identityMapper.updateByPrimaryKeySelective(identity);
    }
}
