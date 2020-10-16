package com.gkjx.saas.health.system.mapper.single;

import com.gkjx.common.data.mybatis.MyMapper;
import com.gkjx.saas.health.system.model.single.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends MyMapper<Role, Long> {

    @Select("SELECT id,organ_id,role_label_id,`name`,platform,remark,is_deleted,create_identity_id,update_identity_id,create_account_id,update_account_id,create_time,update_time FROM role")
    List<Role> selectAllRoles();
}