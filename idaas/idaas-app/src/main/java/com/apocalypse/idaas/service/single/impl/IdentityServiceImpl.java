package com.apocalypse.idaas.service.single.impl;

import cn.hutool.core.util.ObjectUtil;
//import com.gkjx.common.core.enums.error.BusinessErrorCodeEnum;
import com.apocalypse.common.core.enums.error.BusinessErrorCodeEnum;
import com.apocalypse.common.core.util.ServiceExceptionUtil;
import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
//import com.gkjx.saas.health.system.error.SysManageErrorCodeEnum;
import com.apocalypse.idaas.mapper.single.AccountRoleUnionMapper;
import com.apocalypse.idaas.mapper.single.IdentityMapper;
import com.apocalypse.idaas.mapper.single.RoleMapper;
import com.apocalypse.idaas.module.single.Identity;
import com.apocalypse.idaas.service.single.IdentityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
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

}
