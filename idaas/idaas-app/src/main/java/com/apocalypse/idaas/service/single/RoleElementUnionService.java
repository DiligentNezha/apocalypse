package com.gkjx.saas.health.system.service.single;

import com.gkjx.common.data.mybatis.service.BaseService;
import com.gkjx.saas.health.system.model.single.RoleElementUnion;

import java.util.List;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
public interface RoleElementUnionService extends BaseService<RoleElementUnion, Long> {

    List<RoleElementUnion> selectAllRoleElementUnions();

}
