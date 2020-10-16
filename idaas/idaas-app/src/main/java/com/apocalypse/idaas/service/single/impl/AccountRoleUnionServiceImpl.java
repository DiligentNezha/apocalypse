package com.gkjx.saas.health.system.service.single.impl;

import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.mapper.single.RoleMapper;
import com.gkjx.saas.health.system.mapper.single.AccountRoleUnionMapper;
import com.gkjx.saas.health.system.mapper.single.IdentityAccountUnionMapper;
import com.gkjx.saas.health.system.model.single.Role;
import com.gkjx.saas.health.system.model.single.AccountRoleUnion;
import com.gkjx.saas.health.system.model.single.IdentityAccountUnion;
import com.gkjx.saas.health.system.service.single.AccountRoleUnionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
@Slf4j
@Service
public class AccountRoleUnionServiceImpl extends BaseServiceImpl<AccountRoleUnion, Long> implements AccountRoleUnionService {

    @Resource
    private IdentityAccountUnionMapper identityAccountUnionMapper;

    @Resource
    private AccountRoleUnionMapper accountRoleUnionMapper;

    @Resource
    private RoleMapper roleMapper;

}
