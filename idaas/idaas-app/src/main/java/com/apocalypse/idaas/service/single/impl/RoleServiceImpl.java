package com.gkjx.saas.health.system.service.single.impl;

import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.mapper.single.RoleMapper;
import com.gkjx.saas.health.system.model.single.Role;
import com.gkjx.saas.health.system.service.single.OrganService;
import com.gkjx.saas.health.system.service.single.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

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
