package com.apocalypse.idaas.mapper.single;

import com.apocalypse.common.data.mybatis.MyMapper;
import com.apocalypse.idaas.module.single.RoleLabel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleLabelMapper extends MyMapper<RoleLabel, Long> {

    @Select("SELECT id,organ_id,`name`,platform,remark,is_deleted,create_identity_id,update_identity_id,create_account_id,update_account_id,create_time,update_time FROM role_label")
    List<RoleLabel> selectAllRoleLabels();
}