package com.gkjx.saas.health.system.mapper.single;

import com.gkjx.common.data.mybatis.MyMapper;
import com.gkjx.saas.health.system.model.single.RoleElementUnion;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleElementUnionMapper extends MyMapper<RoleElementUnion, Long> {
    @Select("SELECT id,role_id,element_id,is_deleted,create_identity_id,update_identity_id,create_account_id,update_account_id,create_time,update_time FROM role_element_union")
    List<RoleElementUnion> selectAllRoleElementUnions();
}