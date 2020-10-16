package com.apocalypse.idaas.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.AccountRoleUnionMapper;
import com.apocalypse.idaas.mapper.single.IdentityAccountUnionMapper;
import com.apocalypse.idaas.mapper.single.RoleMapper;
import com.apocalypse.idaas.module.single.AccountRoleUnion;
import com.apocalypse.idaas.service.single.AccountRoleUnionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
