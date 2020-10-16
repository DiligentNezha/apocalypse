package com.apocalypse.idaas.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.RoleMapper;
import com.apocalypse.idaas.module.single.Role;
import com.apocalypse.idaas.service.single.OrganService;
import com.apocalypse.idaas.service.single.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private OrganService organService;

    @Override
    public List<Role> selectAllRoles() {
        return roleMapper.selectAllRoles();
    }
}
