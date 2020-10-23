package com.apocalypse.idaas.mapper.single;

import com.apocalypse.common.data.mybatis.MyMapper;
import com.apocalypse.idaas.module.single.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends MyMapper<Role, Long> {

    @Select("SELECT id,organ_id,role_label_id,`name`,remark,is_deleted,create_identity_id,update_identity_id,create_account_id,update_account_id,create_time,update_time FROM role")
    List<Role> selectAllRoles();
}