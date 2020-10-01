package com.apocalypse.idaas.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.RoleDOMapper;
import com.apocalypse.idaas.model.RoleDO;
import com.apocalypse.idaas.service.single.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/10
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDO, Integer> implements RoleService {

    @Autowired
    private RoleDOMapper roleDOMapper;

}
