package com.gkjx.saas.health.system.service.single.impl;

import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.mapper.single.RoleElementUnionMapper;
import com.gkjx.saas.health.system.model.single.RoleElementUnion;
import com.gkjx.saas.health.system.service.single.RoleElementUnionService;
import lombok.extern.slf4j.Slf4j;
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
public class RoleElementUnionServiceImpl extends BaseServiceImpl<RoleElementUnion, Long> implements RoleElementUnionService {

    @Resource
    private RoleElementUnionMapper roleElementUnionMapper;

    @Override
    public List<RoleElementUnion> selectAllRoleElementUnions() {
        return roleElementUnionMapper.selectAllRoleElementUnions();
    }

}
