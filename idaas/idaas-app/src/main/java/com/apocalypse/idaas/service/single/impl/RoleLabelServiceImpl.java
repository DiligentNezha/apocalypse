package com.apocalypse.idaas.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.RoleLabelMapper;
import com.apocalypse.idaas.module.single.RoleLabel;
import com.apocalypse.idaas.service.single.OrganService;
import com.apocalypse.idaas.service.single.RoleLabelService;
import lombok.extern.slf4j.Slf4j;
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
public class RoleLabelServiceImpl extends BaseServiceImpl<RoleLabel, Long> implements RoleLabelService {

    @Resource
    private RoleLabelMapper roleLabelMapper;

    @Resource
    private OrganService organService;

    @Override
    public List<RoleLabel> selectAllRoleLabels() {
        return roleLabelMapper.selectAllRoleLabels();
    }

}
