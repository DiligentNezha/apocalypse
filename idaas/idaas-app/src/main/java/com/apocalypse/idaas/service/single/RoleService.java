package com.apocalypse.idaas.service.single;

import com.apocalypse.common.data.mybatis.service.BaseService;
import com.apocalypse.idaas.module.single.Role;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
public interface RoleService extends BaseService<Role, Long> {

    List<Role> selectAllRoles();

}